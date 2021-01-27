package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.OrderMapper;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dao.TransporterMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.dto.TransporterDto;
import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.service.ITransporterService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransporterServiceImpl implements ITransporterService {
    @Autowired
    private TransporterMapper transporterMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MessageAccessor messageAccessor;
    @Autowired
    private ProductMapper productMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Consts.TIME_FORMAT_MMddyyyyHHmmss);

    @Override
    public List<TransporterForm> getTransporterList() {
        List<TransporterForm> transporterFormList = new ArrayList<>();
        List<TransporterDto> transporterDtoList = userMapper.getTransporterList();
        for (TransporterDto transporterDto : transporterDtoList) {
            TransporterForm transporterForm = new TransporterForm();
            BeanUtils.copyProperties(transporterDto, transporterForm);
            transporterFormList.add(transporterForm);
        }
        return transporterFormList;
    }

    @Override
    public TransporterForm getTransporterInfo(Long transporterId) {
        TransporterDto transporterDto = userMapper.getTransporterInfo(transporterId);
        TransporterForm transporterForm = new TransporterForm();
        BeanUtils.copyProperties(transporterDto, transporterForm);
        return transporterForm;
    }

    @Override
    public List<OrderForm> getOrderList(String orderStatus, Long transporterId) {
        List<OrderDto> orderDtoList = orderMapper.getOrderListTransporter(transporterId);
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
            result.setSuccess(Consts.RESULT_FALSE);
        }
        return result;
    }

    @Override
    public Long getNumberOrderBasedOnStatus(String orderStatus, Long transporterId, Long customerId) {
        Long numberOrder = orderMapper.getNumberOrderBasedOnStatus(orderStatus, transporterId, customerId);
        return numberOrder;
    }
}
