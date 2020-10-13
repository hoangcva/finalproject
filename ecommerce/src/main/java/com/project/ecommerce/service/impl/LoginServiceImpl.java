package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.LoginMapper;
import com.project.ecommerce.dto.UsersDto;
import com.project.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    public LoginMapper loginMapper;

    public List<UsersDto> init() {
        return loginMapper.findAllUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersDto user = loginMapper.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username Not Found");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new User(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, user.getAuthorities());

    }
}
