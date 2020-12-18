package com.project.ecommerce.service;

import com.project.ecommerce.form.CommentForm;
import com.project.ecommerce.util.Message;

public interface ICustomerService {
    Message createComment(Long customerId, Long productId, Long vendorId);
    Message saveComment(CommentForm commentForm);
}
