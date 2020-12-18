package com.project.ecommerce.service;

import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.util.Message;
import org.springframework.security.core.Authentication;

public interface ICustomerService {
    Message createComment(CommentForm commentForm, Authentication auth);
    Message createComment(Long orderId, Authentication auth);
    Message saveComment(CommentForm commentForm);
    CommentForm getComment(CommentForm commentForm);
}
