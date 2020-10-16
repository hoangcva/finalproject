package com.project.ecommerce.service;

import com.project.ecommerce.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ILoginService extends UserDetailsService {
    List<UserDto> init();
}
