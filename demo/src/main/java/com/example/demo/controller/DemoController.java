package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.solita.clamav.ClamAVClient;

@RestController
public class DemoController {

    int threadSafeConfirmValue = 0;

    @GetMapping
    public String doGetDemoSample(){
        threadSafeConfirmValue++;
        // threadSafeConfirmValueはスレッド間で共有されるので、別々のリクエストAを送ってリクエストBを送るとthreadSafeConfirmValueは加算される
        return "スレッドセーフの確認："+ threadSafeConfirmValue;
    }
    
    @GetMapping("/clamAV/ping")
    public String doPing(){
        ClamAVClient clamAVClient = new ClamAVClient("localhost", 3310);
        try{
            boolean pingResult = clamAVClient.ping();
            return String.valueOf(pingResult);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/clamAV/stream")
    public String doStream(){
        String virusText = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*";
        ClamAVClient clamAVClient = new ClamAVClient("localhost", 3310);
        long starttime = System.currentTimeMillis();
        try (InputStream virusInputStream = new ByteArrayInputStream(virusText.getBytes(StandardCharsets.UTF_8))){
            byte[] streamResult = clamAVClient.scan(virusInputStream);
            String streamResultString = new String(streamResult, StandardCharsets.UTF_8).replace("\u0000", "");
            long endtime = System.currentTimeMillis();
            long time = (endtime - starttime);
            return streamResultString + "   time:"+ time;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
