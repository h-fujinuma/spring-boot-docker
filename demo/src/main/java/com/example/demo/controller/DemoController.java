package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${env.var}")
    String envVar;

    @GetMapping
    public String doGetDemoSample(){
        return envVar;
    }
    
}
