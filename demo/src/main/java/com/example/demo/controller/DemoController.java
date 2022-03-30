package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${env.var}")
    String envVar;

    int threadSafeConfirmValue = 0;

    @GetMapping
    public String doGetDemoSample(){
        threadSafeConfirmValue++;
        // threadSafeConfirmValueはスレッド間で共有されるので、別々のリクエストAを送ってリクエストBを送るとthreadSafeConfirmValueは加算される
        return envVar + "スレッドセーフの確認："+ threadSafeConfirmValue;
    }
    
}
