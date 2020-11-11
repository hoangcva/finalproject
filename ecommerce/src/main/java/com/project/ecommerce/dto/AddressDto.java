package com.project.ecommerce.dto;

import java.io.Serializable;
import java.util.List;

public class AddressDto implements Serializable {
    private static final long serialVersionUID = 3354477916071540099L;
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
