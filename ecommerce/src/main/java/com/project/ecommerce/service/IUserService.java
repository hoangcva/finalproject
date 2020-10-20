package com.project.ecommerce.service;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.RegisterForm;
import com.project.ecommerce.form.UserUpdateForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    void createUser(RegisterForm registerForm);
    UserDto findUserByEmail(String email);
    UserDto getUserByUserName(String userName);
    void updateUser(UserUpdateForm userUpdateForm);
}
