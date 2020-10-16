package com.project.ecommerce.dao;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM users")
    List<UserDto> findAllUser();
    UserDto findUserByEmail(@Param("email") String email);
    UserDto findUserByUserName(@Param("user_name") String userName);
    UserDto createUser(UserForm userForm);
}
