package com.dattp.productservice.exception;


import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dattp.productservice.dto.ResponseDTO;

/**
 * HandleBadRequestException
 */
@RestControllerAdvice
public class HandleBadRequestException {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO hanđleException(BindException e){
        return new ResponseDTO(
            HttpStatus.BAD_REQUEST.value(), e.getAllErrors().get(0).getDefaultMessage(),null
        );
    }

    @ExceptionHandler(com.fasterxml.jackson.core.JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO hanđleException(Exception e){
        return new ResponseDTO(
            HttpStatus.BAD_REQUEST.value(), e.getMessage(),null
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerException(BadRequestException e){
        return new ResponseDTO(
            HttpStatus.BAD_REQUEST.value(), e.getMessage(),null
        );
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerException(SQLException e){
        return new ResponseDTO(
            HttpStatus.BAD_REQUEST.value(), e.getMessage(),null
        );
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerException(IOException e){
        return new ResponseDTO(
            HttpStatus.BAD_REQUEST.value(), e.getMessage(),null
        );
    }
}