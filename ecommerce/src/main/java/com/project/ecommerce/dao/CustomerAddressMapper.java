package com.project.ecommerce.dao;

import com.project.ecommerce.dto.CustomerAddressDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerAddressMapper {
    List<CustomerAddressDto> getAllAddressByCustomer(@Param("userName") String userName);
    CustomerAddressDto getAddressById(@Param("id") Long id);
    boolean insertAddress(CustomerAddressDto customerAddressDto);
    boolean updateAddress(CustomerAddressDto customerAddressDto);
    boolean deleteAddress(@Param("id") Long id);
}
