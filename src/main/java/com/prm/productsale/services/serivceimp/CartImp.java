package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.CartItemRequest;
import com.prm.productsale.dto.response.CartItemResponse;
import com.prm.productsale.dto.response.UpdateQuantityResponse;
import com.prm.productsale.entity.CartEntity;
import com.prm.productsale.entity.CartItemEntity;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.CartItemMapper;
import com.prm.productsale.repository.CartItemRepo;
import com.prm.productsale.repository.CartRepo;
import com.prm.productsale.repository.ProductRepo;
import com.prm.productsale.services.CartServices;
import com.prm.productsale.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartImp implements CartServices {
    @Autowired
    CartItemRepo cartItemRepo;
    @Autowired
    LoginServices loginServices;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CartRepo cartRepo;

    @Override
    public void createItem(CartItemRequest request) {
        CartEntity cart = loginServices.getCart();
        ProductEntity product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));
        CartItemEntity cartItem;
        Optional<CartItemEntity> existingCartItemOpt = cartItemRepo.findByCartIdAndProductId(cart.getId(), request.getProductId());
        if(existingCartItemOpt.isPresent()){
            cartItem = existingCartItemOpt.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
        }else{
            cartItem = new CartItemEntity();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        }
        cartItemRepo.save(cartItem);
        List<CartItemEntity> items = cartItemRepo.findByCartId(cart.getId());

        BigDecimal totalPrice = items.stream()
                .map(CartItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotal(totalPrice);

        // Cập nhật lại cart
        cartRepo.save(cart);

    }
    @Transactional
    @Override
    public UpdateQuantityResponse  updateItem(int cartItemId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        CartItemEntity item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        BigDecimal unitPrice = item.getProduct().getPrice();

        item.setQuantity(quantity);
        item.setPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        cartItemRepo.save(item);

        CartEntity cart = item.getCart();
        BigDecimal cartTotal = cartItemRepo.findByCartId(cart.getId()).stream()
                .map(CartItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotal(cartTotal);
        cartRepo.save(cart);

        return UpdateQuantityResponse.builder()
                .itemId(item.getId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .cartTotal(cartTotal)
                .build();
    }

    @Override
    public void deleteItem(int cartItemId) {
        CartItemEntity item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        CartEntity cart = item.getCart();
        cartItemRepo.delete(item);

        BigDecimal newTotal = cartItemRepo.findByCartId(cart.getId()).stream()
                .map(CartItemEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotal(newTotal);
        cartRepo.save(cart);

    }

    @Override
    public Page<CartItemResponse> getListItem(int page,int size) {
        CartEntity cart = loginServices.getCart();

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<CartItemEntity> itemPage = cartItemRepo.findByCartId(cart.getId(), pageable);

        return itemPage.map(CartItemMapper.INSTANCE::toCartItemResponse);
    }

    @Override
    public BigDecimal getTotalAmount() {
        CartEntity cart =
                cartRepo.findByUserId(loginServices.getUser().getId()).orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        return cart.getTotal();
    }

    @Override
    public int getItemCount() {
        CartEntity cart = loginServices.getCart();
        return cartItemRepo.findByCartId(cart.getId())
                .stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
    }
    @Transactional
    @Override
    public void deleteAll() {
        cartItemRepo.deleteAll();
        CartEntity cart = loginServices.getCart();
        cart.setTotal(BigDecimal.ZERO);
        cartRepo.save(cart);
    }
}
