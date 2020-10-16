package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.form.UserForm;
import com.project.ecommerce.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

@Service
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    private UserMapper userMapper;

    public UserDto findUserByEmail(String email) {
        return null;
    }

    public String createUser(UserForm userForm) {

//        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
//        userMapper.createUser(userForm);
        return null;
    }
}
