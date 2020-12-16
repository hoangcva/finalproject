package com.project.ecommerce.dao;

import com.project.ecommerce.dto.OrderDetailDto;
import com.project.ecommerce.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderMapper {
    boolean createOrder(OrderDto orderDto);
    boolean createOrderDetail(OrderDetailDto orderDetailDto);
    List<OrderDto> getOrderListCustomer(@Param("customerId") Long customerId);
    List<OrderDetailDto> getOrderProductList(@Param("orderId") Long orderId);
    OrderDto getOrderDetailCustomer(@Param("orderId") Long orderId);

}
