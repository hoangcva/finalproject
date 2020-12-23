package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.*;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.service.IOrderCustomerService;
import com.project.ecommerce.service.IProductService;
import com.project.ecommerce.util.Message;
import com.project.ecommerce.util.MessageAccessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("orderCustomerService")
public class OrderCustomerServiceImpl implements IOrderCustomerService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ICartService cartService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MessageAccessor messageAccessor;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IProductService productService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @param orderForm
     * @param auth
     * @return
     */
    @Override
    public Message createOrder(OrderForm orderForm, Authentication auth) {
        long buyQuantity;
        long productId;
        long currentQuantity;
        Long newQuantity;
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        String orderDspId = createOrderId(customerId.toString());
        Message result = new Message("", true);
        OrderDto orderDto = new OrderDto();
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        List<ProductForm> productFormList = new ArrayList<>();
        CustomerAddressForm customerAddressForm = customerAddressService.getAddressById(orderForm.getAddressId());
        String deliveryAddress = createDeliveryAddress(customerAddressForm);

        CartInfoForm cartInfoForm = cartService.getCart(customerId);

        orderDto.setCustomerId(customerId);
        orderDto.setBillTotal(cartInfoForm.getBillTotal());
        orderDto.setOrderStatus(Consts.ORDER_STATUS_PROGRESSING);
        orderDto.setDeliveryAddress(deliveryAddress);
//        orderDto.setNote(CommonUtils.nullToString(orderForm.getNote()));
        orderDto.setNote(orderForm.getNote());
        orderDto.setFullName(customerAddressForm.getFullName());
        orderDto.setPhoneNumber(customerAddressForm.getPhoneNumber());
        orderDto.setShippingFee(orderForm.getShippingFee() == null ? 0 : orderForm.getShippingFee());
        orderDto.setOrderDspId(orderDspId);
        orderDto.setTransporterId(orderForm.getTransporterId());

        for (CartLineInfoForm cartLineInfoForm : cartInfoForm.getCartLines()) {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            buyQuantity = cartLineInfoForm.getBuyQuantity();
            productId = cartLineInfoForm.getProductForm().getProductId();
            Long vendorId = cartLineInfoForm.getProductForm().getVendorId();
            orderDetailDto.setProductId(productId);
            orderDetailDto.setVendorId(vendorId);
            orderDetailDto.setBuyQuantity(buyQuantity);
            orderDetailDto.setPrice(cartLineInfoForm.getProductForm().getPrice());
            orderDetailDto.setProductName(cartLineInfoForm.getProductForm().getProductName());
            orderDetailDtoList.add(orderDetailDto);

            ProductForm productForm = productMapper.getVendorProduct(productId, vendorId);
            currentQuantity = productForm.getQuantity();
            newQuantity = currentQuantity - buyQuantity;

            if (newQuantity < 0) {
                result.setMessage(messageAccessor.getMessage(Consts.MSG_05_E, ""));
                result.setSuccess(false);
                return result;
            }
            productForm.setQuantity(newQuantity);
            productFormList.add(productForm);
        }

        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            //create order
            orderMapper.createOrder(orderDto);
            for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
                orderDetailDto.setOrderId(orderDto.getId());
                orderMapper.createOrderDetail(orderDetailDto);
                // update quantity
                newQuantity = productFormList.stream()
                        .filter(productForm -> (productForm.getProductId().equals(orderDetailDto.getProductId() )
                                && productForm.getVendorId().equals(orderDetailDto.getVendorId())))
                        .findAny()
                        .map(ProductForm::getQuantity).orElse((long) -1);

                if (newQuantity == -1) {
                    transactionManager.rollback(txStatus);
                    result.setMessage(messageAccessor.getMessage(Consts.MSG_04_E, ""));
                    result.setSuccess(false);
                    return result;
                }
                productMapper.updateProductQuantity(orderDetailDto.getProductId(),
                                                    orderDetailDto.getVendorId(),
                                                    newQuantity);
            }
            //clear cart
            cartMapper.clearCart(customerId);
            if (!result.isSuccess()) {
                transactionManager.rollback(txStatus);
                result.setMessage(messageAccessor.getMessage(Consts.MSG_04_E, ""));
                result.setSuccess(false);
                return result;
            }
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_05_I, orderDspId));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_04_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    private String createDeliveryAddress(CustomerAddressForm customerAddressForm) {
        StringBuilder deliveryAddress = new StringBuilder();
        if (customerAddressForm.getAddressDetail() != null && !"".equals(customerAddressForm.getAddressDetail())) {
            deliveryAddress.append(customerAddressForm.getAddressDetail());
        }
        deliveryAddress.append(", ");
        deliveryAddress.append(customerAddressForm.getWard());
        deliveryAddress.append(", ");
        deliveryAddress.append(customerAddressForm.getDistrict());
        deliveryAddress.append(", ");
        deliveryAddress.append(customerAddressForm.getProvince());
        return deliveryAddress.toString();
    }

    private String createOrderId(String customerId) {
        StringBuilder orderDspId = new StringBuilder('#');
        LocalDateTime now = LocalDateTime.now();
        String year = ((Integer)now.getYear()).toString();
        String month = ((Integer)now.getMonthValue()).toString();
        String day = ((Integer)now.getDayOfMonth()).toString();
        String hour = ((Integer)now.getHour()).toString();
        String minute = ((Integer)now.getMinute()).toString();
        String second = ((Integer)now.getSecond()).toString();
        orderDspId.append('#');
        orderDspId.append(year);
        orderDspId.append(month);
        orderDspId.append(day);
        orderDspId.append(hour);
        orderDspId.append(minute);
        orderDspId.append(second);
        orderDspId.append(customerId);
        return orderDspId.toString();
    }

    @Override
    public List<OrderForm> getOrderListCustomer(Authentication auth) {
        Long customerId = ((UserDetailsDto) auth.getPrincipal()).getUserDto().getId();
        List<OrderDto> orderDtoList = orderMapper.getOrderListCustomer(customerId);
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
    public OrderForm getOrderDetailCustomer(Long orderId) {
        OrderForm orderForm = new OrderForm();
        List<OrderDetailForm> orderDetailFormList = new ArrayList<>();
        List<ProductForm> productFormList = new ArrayList<>();
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
//            productFormList.add(productForm);
        }
        orderForm.setOrderDetailList(orderDetailFormList);
//        orderForm.setProductFormList(productFormList);
        return orderForm;
    }

    @Override
    public Message cancelOrder(OrderForm orderForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        long newQuantity;
        long vendorId;
        long productId;
        try {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderForm, orderDto);

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

            orderDto.setOrderStatus(Consts.ORDER_STATUS_CANCELED);
            orderMapper.updateOrderStatus(orderDto);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_12_I, orderForm.getOrderDspId()));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_12_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Message finishOrder(OrderForm orderForm) {
        Message result = new Message("", true);
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderForm, orderDto);
            orderDto.setDeliveryDate(LocalDateTime.now());
            orderDto.setOrderStatus(Consts.ORDER_STATUS_SUCCESS);
            orderMapper.updateOrderStatus(orderDto);
            //commit
            transactionManager.commit(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_16_I, orderForm.getOrderDspId()));
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            result.setMessage(messageAccessor.getMessage(Consts.MSG_16_E, ""));
            result.setSuccess(false);
        }
        return result;
    }

}
