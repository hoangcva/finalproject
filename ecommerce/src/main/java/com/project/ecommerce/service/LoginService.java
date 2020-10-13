package com.project.ecommerce.service;

import com.project.ecommerce.dto.UsersDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface LoginService extends UserDetailsService {
    public List<UsersDto> init() throws Exception;
}
