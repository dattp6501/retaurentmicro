package com.dattp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dattp.order.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;

@RestControllerAdvice
public class HandlerRequestException {
    @ExceptionHandler(value =BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerBindException(BindException e){
        return new ResponseDTO(HttpStatus.OK.value(),e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value =BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerBindException(BadRequestException e){
        return new ResponseDTO(HttpStatus.OK.value(),e.getMessage());
    }

    @ExceptionHandler(value =MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerBindException(MethodArgumentNotValidException e){
        return new ResponseDTO(HttpStatus.OK.value(),e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = JsonParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerJSONParseException(JsonParseException e){
        return new ResponseDTO(HttpStatus.OK.value(), e.getMessage());
    }
}