package com.cos.blog.controller.api;

import com.cos.blog.dto.Message;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("api/user")
@RestController
public class UserApiController {
    private final String TAG = "UserApiController: ";

    private final UserService userService;
    private final HttpSession session;

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody User user){
        System.out.println(TAG + "save 호출됨");
        user.setRole(RoleType.USER);
        userService.save(user);
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }

    @PostMapping("login")
    public ResponseEntity<Message> login(@RequestBody User user){
        System.out.println(TAG + "login 호출됨");
        User principal = userService.login(user);
        if(principal != null){
            session.setAttribute("principal", principal);
        }
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }
}
