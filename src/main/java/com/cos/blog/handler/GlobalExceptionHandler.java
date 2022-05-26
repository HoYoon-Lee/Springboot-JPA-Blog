package com.cos.blog.handler;

import com.cos.blog.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleException(Exception e){
        System.out.println("check message: " + e.getMessage());
        return new Message<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())
                .asResponseEntity();
    }
}
