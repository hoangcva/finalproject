package com.project.ecommerce.dao;

import com.project.ecommerce.dto.OrderDetailDto;
import com.project.ecommerce.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderMapper {
    boolean createOrder(OrderDto orderDto);
    boolean createOrderDetail(OrderDetailDto orderDetailDto);
}
