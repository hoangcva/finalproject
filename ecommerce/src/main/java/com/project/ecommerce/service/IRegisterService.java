package com.project.ecommerce.service;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserForm;

public interface IRegisterService {
    UserDto findUserByEmail(String email);
    String createUser(UserForm userForm);
}
