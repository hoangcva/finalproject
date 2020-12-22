package com.project.ecommerce.dao;

import com.project.ecommerce.dto.TransporterDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.dto.VendorDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<UserDto> getAllUser();
    UserDto findUserById(@Param("userId") Long userId);
    UserDto findUserByEmail(@Param("email") String email);
    UserDto findUserByUserName(@Param("user_name") String userName);
    void createUser(UserDto userDto);
    void updateUser(UserDto userDto);
    boolean deleteUser(@Param("userId") Long userId);
    void changePassword(UserDto userDto);

    VendorDto findVendorByEmail(@Param("email") String email);
    int findVendorByUserName(@Param("userName") String userName);
    int findVendorExist(@Param("userName") @Nullable String userName,
                        @Param("email") @Nullable String email,
                        @Param("businessCode") @Nullable String businessCode,
                        @Param("vendorId") @Nullable Long vendorId);
    void createVendor(VendorDto vendorDto);
    boolean deleteVendor(@Param("vendorId") Long vendorId);
    List<VendorDto> getVendorList(@Param("enable") @Nullable Boolean enable);
    boolean activeVendor(@Param("vendorId") Long vendorId);
    boolean lockVendor(@Param("vendorId") Long vendorId);
    VendorDto getVendorInfo(@Param("vendorId") Long vendorId);
    void updateVendor(VendorDto vendorDto);

    void createTransporter(TransporterDto transporterDto);
    List<TransporterDto> getTransporterList();
    TransporterDto getTransporterInfo(@Param("transporterId") Long transporterId);
    void updateTransporterInfo(TransporterDto transporterDto);
    boolean deleteTransporter(@Param("transporterId") Long transporterId);
}
