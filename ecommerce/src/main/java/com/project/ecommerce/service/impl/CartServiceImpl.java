package com.project.ecommerce.service.impl;

import com.project.ecommerce.Consts.Consts;
import com.project.ecommerce.dao.CartMapper;
import com.project.ecommerce.dao.ProductMapper;
import com.project.ecommerce.dto.CartDto;
import com.project.ecommerce.dto.UserDetailsDto;
import com.project.ecommerce.form.CartInfoForm;
import com.project.ecommerce.form.CartLineInfoForm;
import com.project.ecommerce.form.ProductForm;
import com.project.ecommerce.service.ICartService;
import com.project.ecommerce.util.MessageAccessor;
import com.project.ecommerce.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private MessageAccessor messageAccessor;

    @Override
    public CartInfoForm getCart(long customerId) {
        List<CartDto> cartDtoList = cartMapper.getCart(customerId);
        CartInfoForm cartInfoForm = new CartInfoForm();
        for (CartDto cartLine : cartDtoList) {
            ProductForm productForm = productMapper.getProductDetail(cartLine.getProductId(), cartLine.getVendorId());
            cartInfoForm.addCartLine(cartLine.getId(), productForm, cartLine.getBuyQuantity());
        }
        return cartInfoForm;
    }

    @Override
    public Message addProductToCart(CartLineInfoForm cartLineInfoForm, Authentication auth) {
        Message result = new Message();
        String strMessage = "";
        boolean isSuccess = true;
        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();
        CartDto newCartDto = new CartDto(userDetails.getUserDto().getId(),
                cartLineInfoForm.getProductForm().getProductId(),
                cartLineInfoForm.getProductForm().getVendorId(),
                        cartLineInfoForm.getBuyQuantity());
        CartDto existCartDto = cartMapper.findProductInCart(newCartDto);
        if (existCartDto != null) {
//            Long quantity = productMapper.getProductDetail(cartLineInfoForm.getProductForm().getProductId(),
//                    cartLineInfoForm.getProductForm().getVendorId()).getQuantity();
            Long quantity = cartLineInfoForm.getProductForm().getQuantity();
            Long newQuantity = existCartDto.getBuyQuantity() + newCartDto.getBuyQuantity();
            if ( newQuantity > quantity) {
                strMessage = messageAccessor.getMessage(Consts.MSG_01_E, Long.toString(quantity), "");
                isSuccess = false;
            } else {
                TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
                try {
                    cartMapper.updateQuantity(existCartDto.getId(), newQuantity);
                    transactionManager.commit(txStatus);
                } catch (Exception ex) {
                    transactionManager.rollback(txStatus);
                    strMessage = messageAccessor.getMessage(Consts.MSG_02_E, "");
                    isSuccess = false;
                }
            }
        } else {
            TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
            try {
                cartMapper.addProductToCart(newCartDto);
                transactionManager.commit(txStatus);
            } catch (Exception ex) {
                transactionManager.rollback(txStatus);
                strMessage = messageAccessor.getMessage(Consts.MSG_02_E, "");
                isSuccess = false;
            }
        }
        if (strMessage.isEmpty()) {
            strMessage = messageAccessor.getMessage(Consts.MSG_01_I, "");
        }
        result.setSuccess(isSuccess);
        result.setMessage(strMessage);
        return result;
    }

    @Override
    public Message updateQuantity(CartLineInfoForm cartLineInfoForm) {
        Message result = new Message();
        String strMessage = "";
        boolean isSuccess = true;
        CartDto existCartDto = cartMapper.getCartLine(cartLineInfoForm.getId());
        ProductForm productForm = productMapper.getProductDetail(existCartDto.getProductId(), existCartDto.getVendorId());
        long maxQuantity = productForm.getQuantity();
        if (cartLineInfoForm.getBuyQuantity() > maxQuantity) {
            strMessage = messageAccessor.getMessage(Consts.MSG_01_E, Long.toString(maxQuantity));
            cartLineInfoForm.setBuyQuantity(maxQuantity);
        }
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            cartMapper.updateQuantity(cartLineInfoForm.getId(), cartLineInfoForm.getBuyQuantity());
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
            strMessage = messageAccessor.getMessage(Consts.MSG_02_E, "");
            isSuccess = false;
        }
        result.setSuccess(isSuccess);
        result.setMessage(strMessage);
        return result;
    }

    @Override
    public void removeProduct(long id) {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            cartMapper.removeProduct(id);
            transactionManager.commit(txStatus);
        } catch (Exception ex) {
            transactionManager.rollback(txStatus);
        }
    }
}
