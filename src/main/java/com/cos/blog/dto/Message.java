package com.cos.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Message<T> {
    @JsonIgnore
    private HttpStatus httpStatus;
    private int status;
    private T data;

    public Message(HttpStatus httpStatus, T data) {
        this.httpStatus = httpStatus;
        this.data = data;
        status = httpStatus.value();
    }

    public ResponseEntity<Message> asResponseEntity(){
        return new ResponseEntity<>(this, httpStatus);
    }
}
