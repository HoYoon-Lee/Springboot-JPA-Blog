package com.cos.blog.controller.api;

import com.cos.blog.dto.Message;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final String TAG = "UserApiController: ";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("auth/joinProc")
    public ResponseEntity<Message> save(@RequestBody User user){
        System.out.println(TAG + "save 호출됨");
        userService.save(user);
        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }

    @PutMapping("user")
    public ResponseEntity<Message> update(@RequestBody User user){
        String userName = userService.update(user);

        // 세션에 수정된 사용자 정보 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new Message<>(HttpStatus.OK, 1)
                .asResponseEntity();
    }
}
