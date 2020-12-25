package com.project.ecommerce.service;

import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.form.FavoriteForm;
import com.project.ecommerce.util.Message;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICustomerService {
    Message createComment(CommentForm commentForm, Authentication auth);
    Message createComment(Long orderId, Authentication auth);
    Message updateComment(CommentForm commentForm);
    CommentForm getComment(CommentForm commentForm);
    Message addProductToFavorite(Authentication auth, Long productId, Long vendorId);
    List<FavoriteForm> getFavorite(Long customerId);
    Message removeItem(Long id);
    Message removeItem(Long productId, Long vendorId, Long customerId);
    boolean isLiked(Long productId, Long vendorId, Long customerId);
}
