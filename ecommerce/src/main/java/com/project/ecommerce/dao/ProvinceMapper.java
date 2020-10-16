package com.project.ecommerce.dao;

import com.project.ecommerce.dto.ProvinceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProvinceMapper {
    List<ProvinceDto> getAllProvince();
    ProvinceDto fillProvinceByKeyword(@Param("keyword") String keyword);
}
