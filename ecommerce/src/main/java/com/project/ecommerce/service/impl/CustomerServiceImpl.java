package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CommentMapper;
import com.project.ecommerce.dao.OrderMapper;
import com.project.ecommerce.dto.CommentDto;
import com.project.ecommerce.dto.OrderDetailDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.service.ICustomerService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MessageAccessor messageAccessor;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Message createComment(Long orderId, Authentication auth) {
        Message result = new Message("", true);
        StringBuilder message = new StringBuilder();
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        List<OrderDetailDto> orderDetailDtoList = orderMapper.getOrderProductList(orderId);
        for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
            CommentDto commentDto = new CommentDto();
            commentDto.setCustomerId(customerId);
            commentDto.setVendorId(orderDetailDto.getVendorId());
            commentDto.setProductId(orderDetailDto.getProductId());
            try {
                commentMapper.createComment(commentDto);
                //commit
                transactionManager.commit(txStatus);
            } catch (Exception ex) {
                transactionManager.rollback(txStatus);
                message.append(messageAccessor.getMessage(Consts.MSG_14_E,
                        orderDetailDto.getProductName() == null ? "": orderDetailDto.getProductName()));
                result.setSuccess(false);
            }
        }
        result.setMessage(message.toString());
        return result;
    }

    @Override
    public Message createComment(CommentForm commentForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentForm, commentDto);
            commentMapper.createComment(commentDto);
            //commit
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_14_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message saveComment(CommentForm commentForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentForm, commentDto);
            commentDto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            commentMapper.saveComment(commentDto);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_15_I, ""));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_15_E, ""));
            result.setSuccess(false);
        }
        return result;
    }
}
