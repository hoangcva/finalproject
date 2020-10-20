package com.project.ecommerce.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageAccessor {
    @Autowired
    private MessageSource messageSource;

    public MessageAccessor() {
    }

    public String getMessage(String messageId) {
        return this.messageSource.getMessage(messageId, (Object[])null, Locale.getDefault());
    }

    public String getMessage(String messageId, String... args) {
        return this.messageSource.getMessage(messageId, args, Locale.getDefault());
    }
}
