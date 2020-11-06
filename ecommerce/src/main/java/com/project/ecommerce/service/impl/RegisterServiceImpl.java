package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserRegisterForm;
import com.project.ecommerce.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    public void createUser(UserRegisterForm userRegisterForm) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            UserDto userDto = new UserDto();
            userDto.setUserName(userRegisterForm.getUserName());
            userDto.setPassword(encoder.encode(userRegisterForm.getPassword()));
            userDto.setEmail(userRegisterForm.getEmail());
            userDto.setFullName(userRegisterForm.getFullName());
            userDto.setRole("ROLE_USER");
            userMapper.createUser(userDto);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }
}
