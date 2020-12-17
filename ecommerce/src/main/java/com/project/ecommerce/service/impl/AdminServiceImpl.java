package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CategoryMapper;
import com.project.ecommerce.dao.OrderMapper;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.form.SubCategoryForm;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.service.IAdminService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MessageAccessor messageAccessor;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Add sub-category
     * @param subCategoryForm
     * @return
     */
    @Override
    public Message addCategory(SubCategoryForm subCategoryForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            SubCategoryDto subCategoryDto = new SubCategoryDto();
            BeanUtils.copyProperties(subCategoryForm, subCategoryDto);
            categoryMapper.addSubCategory(subCategoryDto);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_06_I, ""));
            //commit
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_06_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message addTransporter(TransporterForm transporterForm, Authentication auth) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            long userId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            transporterForm.setCreatedUser(userId);
            TransporterDto transporterDto = new TransporterDto();
            BeanUtils.copyProperties(transporterForm, transporterDto);
            transporterDto.setRole(Consts.ROLE_SHIPPER);
            transporterDto.setPassword(encoder.encode(transporterForm.getPassword()));
            userMapper.createUser(transporterDto);
//            transporterDto.setTransportedId(transporterDto.getId());
            userMapper.createTransporter(transporterDto);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_10_I, ""));
            //commit
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_10_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message updateTransporterInfo(TransporterForm transporterForm, Authentication auth) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            long userId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
            transporterForm.setUpdatedUser(userId);
            TransporterDto transporterDto = new TransporterDto();
            BeanUtils.copyProperties(transporterForm, transporterDto);
            transporterDto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            UserDto userDto = transporterDto;
            userDto.setId(userId);
            userMapper.updateTransporterInfo(transporterDto);
            userMapper.updateUser(userDto);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_11_I, ""));
            //commit
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_11_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<OrderForm> getOrderList(String orderStatus) {
        List<OrderDto> orderDtoList = orderMapper.getOrderList(orderStatus);
        List<OrderForm> orderFormList = new ArrayList<>();
        for (OrderDto orderDto : orderDtoList) {
            OrderForm orderForm = new OrderForm();
            BeanUtils.copyProperties(orderDto, orderForm);
            orderForm.setOrderDate(orderDto.getOrderDate() == null ? "": orderDto.getOrderDate().format(formatter));
            orderForm.setDeliveryDate(orderDto.getDeliveryDate() == null ? "": orderDto.getDeliveryDate().format(formatter));
            orderFormList.add(orderForm);
        }
        return orderFormList;
    }

    @Override
    public Message updateOrderStatus(OrderForm orderForm) {
        Message result = new Message("", true);
        long newQuantity;
        long vendorId;
        long productId;
        String orderStatus = orderForm.getOrderStatus();
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderForm, orderDto);
            orderDto.setDeliveryDate(LocalDateTime.now());
            orderMapper.updateOrderStatus(orderDto);

            if (Consts.ORDER_STATUS_CANCELED.equals(orderStatus)) {
                // update quantity
                List<OrderDetailDto> orderDetailDtoList = orderMapper.getOrderProductList(orderDto.getId());
                for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
                    vendorId = orderDetailDto.getVendorId();
                    productId = orderDetailDto.getProductId();
                    long currentQuantity = productMapper.getProductQuantity(productId, vendorId);
                    newQuantity = orderDetailDto.getBuyQuantity() + currentQuantity;
                    productMapper.updateProductQuantity(orderDetailDto.getProductId(),
                            orderDetailDto.getVendorId(),
                            newQuantity);
                }
            }
            //commit
            transactionManager.commit(txStatus);
            if (Consts.ORDER_STATUS_CANCELED.equals(orderStatus)) {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_12_I, orderForm.getOrderDspId()));
            } else if (Consts.ORDER_STATUS_DELIVERING.equals(orderStatus)) {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_13_I, orderForm.getOrderDspId()));
            }
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            if (Consts.ORDER_STATUS_CANCELED.equals(orderStatus)) {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_12_E, ""));
            } else if (Consts.ORDER_STATUS_DELIVERING.equals(orderStatus)) {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_13_E, ""));
            }
            result.setSuccess(false);
        }
        return result;
    }
}
