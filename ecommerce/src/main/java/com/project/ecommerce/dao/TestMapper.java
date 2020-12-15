package com.project.ecommerce.dao;

import com.project.ecommerce.dto.TestDto;
import com.project.ecommerce.form.TestForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestMapper {
    boolean save(TestDto testDto);
    TestDto get();
}
