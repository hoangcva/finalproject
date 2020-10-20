package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
    @Autowired
    public UserMapper userMapper;

    public List<UserDto> init() {
        return userMapper.findAllUser();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userMapper.findUserByUserName(username);
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
