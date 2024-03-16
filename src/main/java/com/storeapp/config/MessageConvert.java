package com.storeapp.config;

import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class MessageConvert extends AbstractMessageConverter {
    @Override
    protected boolean supports(Class<?> clazz) {
        return false;
    }

    public MessageConvert() {
            super(new MimeType("application", "mymessage"));

    }

}
