package com.project.ecommerce.service.impl;

import com.project.ecommerce.dao.TransporterMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.TransporterDto;
import com.project.ecommerce.form.OrderForm;
import com.project.ecommerce.form.TransporterForm;
import com.project.ecommerce.service.ITransporterService;
import com.project.ecommerce.util.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransporterServiceImpl implements ITransporterService {
    @Autowired
    private TransporterMapper transporterMapper;
    @Autowired
    private UserMapper userMapper;

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
    public List<OrderForm> getOrderList(String orderStatus) {
        return null;
    }

    @Override
    public Message updateOrderStatus(OrderForm orderForm) {
        return null;
    }
}
