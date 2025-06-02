package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.response.OrderResponse;
import com.prm.productsale.entity.OrderEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.OrderMapper;
import com.prm.productsale.repository.OrderRepo;
import com.prm.productsale.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderImp implements OrderServices {
    @Autowired
    OrderRepo orderRepo;
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAll() {
        List<OrderEntity> orderEntity = orderRepo.findAll();
        if(orderEntity.isEmpty()) throw new AppException(ErrorCode.ORDER_NOT_EXIST);
        return OrderMapper.INSTANCE.toOrderResponseList(orderEntity);
    }
}
