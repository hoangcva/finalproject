package com.project.ecommerce.service;

import com.project.ecommerce.form.OrderForm;

public interface IOrderAdminService {
    OrderForm getOrderDetail(Long orderId);
}
