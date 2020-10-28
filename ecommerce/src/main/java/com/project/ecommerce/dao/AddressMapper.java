package com.project.ecommerce.dao;

import com.project.ecommerce.dto.DistrictDto;
import com.project.ecommerce.dto.ProvinceDto;
import com.project.ecommerce.dto.WardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AddressMapper {
    List<ProvinceDto> getAllProvince();
    List<DistrictDto> getAllDistrict(@Param("provinceId") Long provinceId);
    List<WardDto> getAllWard(@Param("provinceId") Long provinceId, @Param("districtId") Long districtId);
}
