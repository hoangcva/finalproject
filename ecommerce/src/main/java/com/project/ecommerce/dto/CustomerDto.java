package com.project.ecommerce.dto;

import java.io.Serializable;
import java.util.List;

public class CustomerDto extends UserDto {
    private static final long serialVersionUID = -536649945783453310L;
    private List<CustomerAddressDto> customerAddressDtoList;

    public List<CustomerAddressDto> getCustomerAddressDtoList() {
        return customerAddressDtoList;
    }

    public void setCustomerAddressDtoList(List<CustomerAddressDto> customerAddressDtoList) {
        this.customerAddressDtoList = customerAddressDtoList;
    }
}
