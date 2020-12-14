package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CartMapper;
import com.project.ecommerce.dao.OrderMapper;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.OrderDetailDto;
import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.*;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.service.ICustomerAddressService;
import com.project.ecommerce.service.IOrderCustomerService;
import com.project.ecommerce.util.CommonUtils;
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
        orderDto.setNote(CommonUtils.nullToString(orderForm.getNote()));
        orderDto.setNote(orderForm.getNote());
        orderDto.setFullName(customerAddressForm.getFullName());
        orderDto.setPhoneNumber(customerAddressForm.getPhoneNumber());
        orderDto.setShippingFee(orderForm.getShippingFee() == null ? 0 : orderForm.getShippingFee());
        orderDto.setOrderDspId(orderDspId);

        for (CartLineInfoForm cartLineInfoForm : cartInfoForm.getCartLines()) {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            buyQuantity = cartLineInfoForm.getBuyQuantity();
            productId = cartLineInfoForm.getProductForm().getProductId();

            orderDetailDto.setProductId(productId);
            orderDetailDto.setVendorId(cartLineInfoForm.getProductForm().getVendorId());
            orderDetailDto.setBuyQuantity(buyQuantity);
            orderDetailDto.setPrice(cartLineInfoForm.getProductForm().getPrice());
            orderDetailDtoList.add(orderDetailDto);

            ProductForm productForm = productMapper.getVendorProduct(productId);
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
            orderFormList.add(orderForm);
        }
        return orderFormList;
    }

    @Override
    public OrderForm getOrderDetailCustomer(Long orderId) {
        return null;
    }
}
