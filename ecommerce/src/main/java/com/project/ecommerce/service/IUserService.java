package com.project.ecommerce.service;

import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.PasswordForm;
import com.project.ecommerce.form.UserRegisterForm;
import com.project.ecommerce.form.UserUpdateForm;
import com.project.ecommerce.form.VendorForm;
import com.project.ecommerce.util.Message;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    void createUser(UserRegisterForm userRegisterForm);
    UserDto findUserByEmail(String email);
    Message updateUser(UserUpdateForm userUpdateForm);
    List<UserDto> getAllUser();
    Message deleteUser(Long userId);
    List<VendorForm> getVendorList(@Nullable Boolean enable);
    Message activeVendor(Long vendorId, Boolean enable);
    Message changePassword(PasswordForm passwordForm, Authentication auth);
}
