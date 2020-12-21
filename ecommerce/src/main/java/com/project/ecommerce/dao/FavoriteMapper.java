package com.project.ecommerce.dao;

import com.project.ecommerce.form.FavoriteForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FavoriteMapper {
    void addProductToFavorite(@Param("productId") Long productId,
                              @Param("vendorId") Long vendorId,
                              @Param("customerId") Long customerId);

    List<FavoriteForm> getFavorite(@Param("customerId") Long customerId);
    void removeItem(@Param("id") Long id);
}
