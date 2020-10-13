package com.project.ecommerce.dao;

import com.project.ecommerce.dto.UsersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LoginMapper {
//    @Select("SELECT * FROM users")
    List<UsersDto> findAllUser();

    UsersDto findUserByName(@Param("user_name") String strUserName);
}
