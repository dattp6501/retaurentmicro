package com.dattp.notifitationservice.config;

import org.springframework.http.HttpStatus;

public class ApplicationConfig {
    public static final int OK = 200;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int DEFAULT = 300;


    public static final HttpStatus HTTP_STATUS_OK = HttpStatus.OK;
    public static final HttpStatus HTTP_STATUS_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final HttpStatus HTTP_STATUS_BAD_REQUEST = HttpStatus.BAD_REQUEST;



}
