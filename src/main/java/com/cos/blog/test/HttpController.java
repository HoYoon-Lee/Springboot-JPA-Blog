package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

@RequestMapping("http")
@RestController
public class HttpController {
    private static final String TAG = "HttpController: ";

    @GetMapping("get")
    public String getTest(Member m){
        return "Get 요청: " + String.join(", ", String.valueOf(m.getId()), m.getUsername(), m.getPassword(), m.getEmail());
    }

    @PostMapping("post")
    public String postTest(@RequestBody Member m){
        return "Post 요청: " + String.join(", ", String.valueOf(m.getId()), m.getUsername(), m.getPassword(), m.getEmail());
    }

    @PutMapping("put")
    public String putTest(@RequestBody Member m){
        return "Put 요청: " + String.join(", ", String.valueOf(m.getId()), m.getUsername(), m.getPassword(), m.getEmail());
    }

    @DeleteMapping("delete")
    public String deleteTest(){
        return "Delete 요청";
    }
}
