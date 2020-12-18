package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.service.ICustomerService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Override
    public Message createComment(Long customerId, Long productId, Long vendorId) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderForm, orderDto);
            orderDto.setDeliveryDate(LocalDateTime.now());
            orderMapper.updateOrderStatus(orderDto);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_13_I, orderForm.getOrderDspId()));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_13_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message saveComment(CommentForm commentForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderForm, orderDto);
            orderDto.setDeliveryDate(LocalDateTime.now());
            orderMapper.updateOrderStatus(orderDto);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_13_I, orderForm.getOrderDspId()));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_13_E, ""));
            result.setSuccess(false);
        }
        return result;
    }
}
