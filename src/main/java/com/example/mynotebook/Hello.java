package com.example.mynotebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Hello {
    @RequestMapping("/helloword")
    public String helloword() {
        return "hello";
    }
}
