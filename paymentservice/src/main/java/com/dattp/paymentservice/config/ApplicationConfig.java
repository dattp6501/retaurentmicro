package com.dattp.paymentservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    public static final int UNPAID_STATE = 0;
    public static final int PAID_STATE = 1;
}