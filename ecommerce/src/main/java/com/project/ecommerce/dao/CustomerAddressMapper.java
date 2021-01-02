package com.project.ecommerce.dao;

import com.project.ecommerce.dto.CustomerAddressDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerAddressMapper {
    List<CustomerAddressDto> getAllAddressByCustomerId(@Param("customerId") Long customerId);
    CustomerAddressDto getAddressById(@Param("id") Long id);
    boolean insertAddress(CustomerAddressDto customerAddressDto);
    boolean updateAddress(CustomerAddressDto customerAddressDto);
    boolean deleteAddress(@Param("id") Long id);
    boolean deleteALLAddressByUser(@Param("customerId") Long customerId);
    CustomerAddressDto getDefaultAddress(@Param("customerId") Long customerId);
    boolean setDefault(@Param("id") Long id);
    boolean clearDefault(@Param("id") Long id);
    boolean updateDefault(@Param("id") Long id);
    int countAddress(@Param("customerId") Long customerId);
}
