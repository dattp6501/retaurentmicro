package com.dattp.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dattp.paymentservice.dto.ResponseDTO;

@RestControllerAdvice
public class HandlerException {
    


    @ExceptionHandler(value =Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO handlerException(Exception e){
        return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),null);
    }
}
