package com.project.ecommerce.dao;

import com.project.ecommerce.dto.VendorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VendorMapper {
    VendorDto findVendorByEmail(@Param("email") String email);
    int findVendorByUserName(@Param("userName") String userName);
    int findVendorExist(@Param("userName") @Nullable String userName, @Param("email") @Nullable String email, @Param("businessCode") @Nullable String businessCode);
    void createVendor(VendorDto vendorDto);
    VendorDto getInfo(@Param("vendorId") Long vendorId);
    void updateVendor(VendorDto vendorDto);
}
