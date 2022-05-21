package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("temp")
public class TempController {
    @GetMapping("home")
    public String tempHome(){
        System.out.println("tempHome()");
        return "/home.html";
    }

    @GetMapping("jsp")
    public String tempjsp(){
        return "test";
    }
}
