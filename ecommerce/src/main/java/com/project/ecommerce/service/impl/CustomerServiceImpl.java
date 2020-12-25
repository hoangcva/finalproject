package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CommentMapper;
import com.project.ecommerce.dao.FavoriteMapper;
import com.project.ecommerce.dao.OrderMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.form.FavoriteForm;
import com.project.ecommerce.service.ICustomerService;
import com.project.ecommerce.service.IUserService;
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
import java.text.SimpleDateFormat;
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
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;

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
    public Message createComment(CommentForm commentForm, Authentication auth) {
        Message result = new Message("", true);
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentForm, commentDto);
            //get customer name
            UserDto userDto = userMapper.findUserById(customerId);
            commentDto.setCustomerName(userDto.getFullName());
            //get vendor name
            VendorDto vendorDto = userMapper.getVendorInfo(commentForm.getVendorId());
            commentDto.setVendorName(vendorDto.getVendorName());
            commentDto.setCustomerId(customerId);
            commentMapper.createComment(commentDto);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_14_I, ""));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_14_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message updateComment(CommentForm commentForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentForm, commentDto);
            commentDto.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            commentMapper.updateComment(commentDto);
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

    @Override
    public CommentForm getComment(CommentForm commentForm) {
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(commentForm, commentDto);
        commentDto = commentMapper.getComment(commentDto);
        if (commentDto != null) {
            BeanUtils.copyProperties(commentDto, commentForm);
            commentForm.setCreatedTime(new SimpleDateFormat(Consts.TIME_FORMAT_MMddyyyyHHmmss).format(commentDto.getCreatedTime()));
        }
        return commentForm;
    }

    @Override
    public Message addProductToFavorite(Authentication auth, Long productId, Long vendorId) {
        Message result = new Message("", true);
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            favoriteMapper.addProductToFavorite(productId, vendorId, customerId);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_22_I, ""));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_22_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<FavoriteForm> getFavorite(Long customerId) {
        List<FavoriteForm> favoriteFormList = favoriteMapper.getFavorite(customerId);
        return favoriteFormList;
    }

    @Override
    public Message removeItem(Long id) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            favoriteMapper.removeItem1(id);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_23_I, ""));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_23_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message removeItem(Long productId, Long vendorId, Long customerId) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            favoriteMapper.removeItem2(productId, vendorId, customerId);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_23_I, ""));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_23_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public boolean isLiked(Long productId, Long vendorId, Long customerId) {
        int count = favoriteMapper.isLiked(productId, vendorId, customerId);
        boolean isLiked = count > 0 ? true : false;
        return isLiked;
    }
}
