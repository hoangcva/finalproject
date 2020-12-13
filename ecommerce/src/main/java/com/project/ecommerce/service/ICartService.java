package com.project.ecommerce.service;

import com.project.ecommerce.form.CartInfoForm;
import com.project.ecommerce.form.CartLineInfoForm;
import com.project.ecommerce.util.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;

public interface ICartService {
    //    void createCart(@Param("customerId") long customerId);
    CartInfoForm getCart(long customerId);
    Message addProductToCart(CartLineInfoForm cartLineInfoForm, Authentication auth);
    Message updateQuantity(CartLineInfoForm cartLineInfoForm);
    Message removeProduct(long id);
    Message clearCart(long customerId);
}
