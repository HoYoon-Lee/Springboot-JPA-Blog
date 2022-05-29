package com.cos.blog.controller;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Value("${cos.key}")
    private String cosKey;

    @GetMapping("auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("auth/kakao/callback")
    public String kakaoCallback(String code){
        String requestTokenUrl = "https://kauth.kakao.com/oauth/token";
        String requestProfileUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "2895dbd09e0eb960b8ce21d01ce9b66b");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                requestTokenUrl,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        try {
            OAuthToken oAuthToken = mapper.readValue(response.getBody(), OAuthToken.class);
            System.out.println("카카오 엑세스 토큰: " + oAuthToken.getAccessToken());

            headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

            HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
            response = restTemplate.exchange(
                    requestProfileUrl,
                    HttpMethod.POST,
                    kakaoProfileRequest,
                    String.class
            );

            KakaoProfile kakaoProfile = mapper.readValue(response.getBody(), KakaoProfile.class);

            User user = User.builder()
                    .userName(kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId())
                    .password(cosKey)
                    .email(kakaoProfile.getKakaoAccount().getEmail())
                    .oauth("kakao")
                    .build();

            if(!userService.existsByUserName(user.getUserName()))
                userService.save(user);
            else
                System.out.println("기존 회원입니다!");

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), cosKey));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("user/myPage")
    public String updateForm(){
        return "user/updateForm";
    }
}
