package com.project.ecommerce.service;

import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.form.SubCategoryForm;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.util.Message;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IAdminService {
    Message addCategory(SubCategoryForm subCategoryForm);
    Message addTransporter(TransporterForm transporterForm, Authentication auth);
    Message updateTransporterInfo(TransporterForm transporterForm, Authentication auth);
    List<OrderForm> getOrderList(String orderStatus);
    Message updateOrderStatus(OrderForm orderForm);
    Long getNumberOrderBasedOnStatus(String orderStatus,Long transporterId,Long customerId);
    int countOrder();
    int countProgressingOrder();
}
