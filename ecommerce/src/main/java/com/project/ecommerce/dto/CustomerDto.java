package com.project.ecommerce.dto;

import java.util.List;

public class CustomerDto extends UserDto{
    private List<CustomerAddressDto> customerAddressDtoList;

    public List<CustomerAddressDto> getCustomerAddressDtoList() {
        return customerAddressDtoList;
    }

    public void setCustomerAddressDtoList(List<CustomerAddressDto> customerAddressDtoList) {
        this.customerAddressDtoList = customerAddressDtoList;
    }
}
