package com.project.ecommerce.service;

import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.util.Message;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IOrderCustomerService {
    Message createOrder(OrderForm orderForm, Authentication auth);
    List<OrderForm> getOrderListCustomer(Authentication auth);
    OrderForm getOrderDetailCustomer(Long orderId);
}
