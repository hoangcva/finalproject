package com.project.ecommerce.dao;

import com.project.ecommerce.dto.CartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CartMapper {
//    void createCart(@Param("customerId") long customerId);
    List<CartDto> getCart(@Param("customerId") long customerId);
    void addProductToCart(CartDto cartDto);
    void updateQuantity(@Param("id") long id, @Param("buyQuantity") long buyQuantity);
    void removeProduct(@Param("id") long id);
    CartDto findProductInCart(CartDto cartDto);
}
