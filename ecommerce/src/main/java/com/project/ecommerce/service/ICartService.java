package com.project.ecommerce.service;

import com.project.ecommerce.form.CartInfoForm;
import com.project.ecommerce.form.CartLineInfoForm;
import com.project.ecommerce.util.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;

public interface ICartService {
    //    void createCart(@Param("customerId") long customerId);
    CartInfoForm getCart(@Param("customerId") long customerId);
    Message addProductToCart(CartLineInfoForm cartLineInfoForm, Authentication auth);
    Message updateQuantity(@Param("id") CartLineInfoForm cartLineInfoForm);
    void removeProduct(@Param("id") long id);
}
