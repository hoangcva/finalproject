package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CartMapper;
import com.project.ecommerce.dao.OrderMapper;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dao.UserMapper;
import com.project.ecommerce.dto.OrderDetailDto;
import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.dto.VendorDto;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.service.IOrderAdminService;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderAdminServiceImpl implements IOrderAdminService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IProductService productService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Consts.TIME_FORMAT_MMddyyyyHHmmss);

    @Override
    public OrderForm getOrderDetail(Long orderId) {
        OrderForm orderForm = new OrderForm();
        List<OrderDetailForm> orderDetailFormList = new ArrayList<>();
        List<OrderDetailDto> orderDetailDtoList = orderMapper.getOrderProductList(orderId);
        OrderDto orderDto = orderMapper.getOrderDetailCustomer(orderId);

        BeanUtils.copyProperties(orderDto, orderForm);
        for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
            ProductForm productForm = productMapper.getProductDetail(orderDetailDto.getProductId(),
                                                                    orderDetailDto.getVendorId());
            List<ProductImageForm> productImageFormList = productService.getProductImage(orderDetailDto.getProductId());
            productForm.setProductImageFormList(productImageFormList);
            VendorDto vendorDto = userMapper.getVendorInfo(orderDetailDto.getVendorId());
            VendorForm vendorForm = new VendorForm();
            BeanUtils.copyProperties(vendorDto, vendorForm);
            OrderDetailForm orderDetailForm = new OrderDetailForm();
            BeanUtils.copyProperties(orderDetailDto, orderDetailForm);
            orderDetailForm.setProductForm(productForm);
            orderDetailForm.setVendorForm(vendorForm);
            orderDetailFormList.add(orderDetailForm);
        }
        orderForm.setOrderDetailList(orderDetailFormList);
        return orderForm;
    }
}
