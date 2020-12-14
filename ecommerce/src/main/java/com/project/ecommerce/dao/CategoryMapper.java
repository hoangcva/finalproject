package com.project.ecommerce.dao;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.SubCategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper {
    List<CategoryDto> getAllCategory();
    List<SubCategoryDto> getALLSubCategory();
    CategoryDto findCategory(Integer categoryId);
    SubCategoryDto findSubCategory(Integer categoryId);

    boolean addSubCategory(SubCategoryDto subCategoryDto);
}
