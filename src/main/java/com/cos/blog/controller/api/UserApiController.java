package com.cos.blog.controller.api;

import com.cos.blog.dto.Message;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final String TAG = "UserApiController: ";

    private final UserService userService;

    @PostMapping("auth/joinProc")
    public ResponseEntity<Message> save(@RequestBody User user){
        System.out.println(TAG + "save 호출됨");
        userService.save(user);
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }
}
