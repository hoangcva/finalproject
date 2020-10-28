package com.project.ecommerce.dto;

import java.util.List;

public class AddressDto {
    private List<ProvinceDto> provinceDtoList;
    private List<DistrictDto> districtDtoList;
    private List<WardDto> wardDtoList;

    public List<ProvinceDto> getProvinceDtoList() {
        return provinceDtoList;
    }

    public void setProvinceDtoList(List<ProvinceDto> provinceDtoList) {
        this.provinceDtoList = provinceDtoList;
    }

    public List<DistrictDto> getDistrictDtoList() {
        return districtDtoList;
    }

    public void setDistrictDtoList(List<DistrictDto> districtDtoList) {
        this.districtDtoList = districtDtoList;
    }

    public List<WardDto> getWardDtoList() {
        return wardDtoList;
    }

    public void setWardDtoList(List<WardDto> wardDtoList) {
        this.wardDtoList = wardDtoList;
    }
}
