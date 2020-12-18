package com.project.ecommerce.service;

import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.util.Message;

import java.util.List;

public interface ITransporterService {
    List<TransporterForm> getTransporterList();
    TransporterForm getTransporterInfo(Long transporterId);
    List<OrderForm> getOrderList(String orderStatus, Long transporterId);
    Message updateOrderStatus(OrderForm orderForm);
}
