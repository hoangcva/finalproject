package com.project.ecommerce.dao;

import com.project.ecommerce.dto.OrderDetailDto;
import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.dto.VendorStatisticDto;
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
    boolean updateOrderStatus(OrderDto orderDto);
    List<OrderDto> getOrderList(@Param("orderStatus") String orderStatus, @Param("transporterId") Long transporterId);
    List<OrderDto> getOrderListTransporter(@Param("transporterId") Long transporterId);

    Long totalIncomeLast30Days(@Param("vendorId") Long vendorId);
    Long totalIncome(@Param("vendorId") Long vendorId);
    Long todayIncome(@Param("vendorId") Long vendorId);
    List<VendorStatisticDto> soldProduct(@Param("vendorId") Long vendorId);
    Long getNumberOrderBasedOnStatus(@Param("orderStatus") String orderStatus,
                                     @Param("transporterId") Long transporterId,
                                     @Param("customerId") Long customerId);
    String getOrderStatus(@Param("orderId") Long orderId);
    int countOrder();
    int countProgressingOrder();
}
