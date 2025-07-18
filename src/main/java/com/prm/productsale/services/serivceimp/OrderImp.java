package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.CartItemRequest;
import com.prm.productsale.dto.request.OrderRequest;
import com.prm.productsale.dto.request.ReorderRequest;
import com.prm.productsale.dto.response.OrderHistoryResponse;
import com.prm.productsale.dto.response.OrderResponse;
import com.prm.productsale.entity.CartEntity;
import com.prm.productsale.entity.CartItemEntity;
import com.prm.productsale.entity.OrderEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.OrderHistoryMapper;
import com.prm.productsale.mapper.OrderMapper;
import com.prm.productsale.repository.CartRepo;
import com.prm.productsale.repository.OrderRepo;
import com.prm.productsale.repository.UserRepo;
import com.prm.productsale.services.CartServices;
import com.prm.productsale.services.OrderServices;
import com.prm.productsale.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderImp implements OrderServices {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CartServices cartServices;
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAll() {
        List<OrderEntity> orderEntity = orderRepo.findAll();
        if(orderEntity.isEmpty()) throw new AppException(ErrorCode.ORDER_NOT_EXIST);
        return OrderMapper.INSTANCE.toOrderResponseList(orderEntity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<OrderResponse> findByStatus(String status) {
        List<OrderEntity> orderEntityList = orderRepo.findByOrderStatus(status);
        if (orderEntityList.isEmpty()) throw new AppException(ErrorCode.ORDER_NOT_EXIST);
        return OrderMapper.INSTANCE.toOrderResponseList(orderEntityList);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void editStatus(int orderId,String status) {
        if (!status.equalsIgnoreCase("pending") &&
                !status.equalsIgnoreCase("shipped") &&
                !status.equalsIgnoreCase("cancel")&&
                !status.equalsIgnoreCase("delivered")) {
            throw new AppException(ErrorCode.INVALID_FORMAT);
        }
        OrderEntity order = orderRepo.findById(orderId).orElseThrow(()->new AppException(ErrorCode.ORDER_NOT_EXIST));
        order.setOrderStatus(status);
        orderRepo.save(order);
    }
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private CartRepo cartRepository;
    @Override
    public OrderEntity createOrder(OrderRequest request) {
        OrderEntity order = new OrderEntity();
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        order.setUser(user);
        CartEntity cart = cartRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        order.setCart(cart);
        order.setPmMethod(request.getPaymentMethod());
        order.setBill(user.getAddress());
        order.setOrderDate(LocalDateTime.now());
        // Logic payment method
        if ("COD".equalsIgnoreCase(request.getPaymentMethod())) {
            order.setOrderStatus("shipping");
        } else if ("MOMO".equalsIgnoreCase(request.getPaymentMethod())) {
            order.setOrderStatus("pending");
        } else {
            order.setOrderStatus("pending");
        }

        return orderRepo.save(order);
    }

    @Override
    public OrderEntity getOrder(int id) {
        return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void markCartInactive(CartEntity cart) {
        cart.setStatus("INACTIVE");
        cartRepository.save(cart);
    }

    @Override
    public List<OrderHistoryResponse> getOrdersByUserId(int userId) {
        List<OrderEntity> orders = orderRepo.findByUserId(userId);
        return orders.stream()
                .map(OrderHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CartItemEntity> getCartItemsByOrderId(int orderId) {
        OrderEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        CartEntity  cart = order.getCart();
        return cart.getCartItems();
    }

    @Override
    public void updateOrder(int orderId) {
        OrderEntity order = orderRepo.getReferenceById(orderId);
        order.setOrderStatus("arrived");
        orderRepo.save(order);
    }

    @Override
    public void reorder(ReorderRequest request) {
        cartServices.deleteAll();
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_FORMAT);
        }

        for (ReorderRequest.Item item : request.getItems()) {
            CartItemRequest cartItemRequest = new CartItemRequest();
            cartItemRequest.setProductId(item.getProductId());
            cartItemRequest.setQuantity(item.getQuantity());

            cartServices.createItem(cartItemRequest);
        }
    }
}
