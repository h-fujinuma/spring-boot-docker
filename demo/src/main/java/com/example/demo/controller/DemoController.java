package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.solita.clamav.ClamAVClient;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
@RestController
public class DemoController {
    
    @Autowired
    private Storage storage;

    private String hostName = "localhost";
    private int port = 3310;
    private int timeout = 5000;
    private String tmpFileParentPath = "/";
    private static final int CHUNK_SIZE = 2048;

    @GetMapping("/clamav/ping")
    public String doPing(){
        ClamAVClient clamAVClient = new ClamAVClient("localhost", 3310);
        try{
            boolean pingResult = clamAVClient.ping();
            return String.valueOf(pingResult);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/clamav/instream")
    public String doInstream(@RequestParam("filePath") String filePath){
        ClamAVClient clamAVClient = new ClamAVClient(hostName, port, timeout);
        try (InputStream inputStream = downloadFileFromGCS(filePath)){
            byte[] streamResult = clamAVClient.scan(inputStream);
            String streamResultString = new String(streamResult, StandardCharsets.UTF_8).replace("\u0000", "");
            return "INSTREAM Result : " + streamResultString;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/clamav/scan")
    public String doScan(@RequestParam("filePath") String filePath){
        String result;
        try(InputStream inputStream = downloadFileFromGCS(filePath)){
            writeFile2Local(inputStream, filePath);
            // scanコマンドの実装
            try (Socket s = new Socket(hostName,port); OutputStream outs = new BufferedOutputStream(s.getOutputStream())) {
                s.setSoTimeout(timeout);
                String fileFullPath = new File(tmpFileParentPath + filePath).getAbsolutePath();
                String command = "zSCAN " + fileFullPath + "\0";
                outs.write(asBytes(command));
                outs.flush();
                byte[] chunk = new byte[CHUNK_SIZE];

                InputStream inputStreamClamav = s.getInputStream();
                int copyIndex = 0;
                int readResult;
                do {
                  readResult = inputStreamClamav.read(chunk, copyIndex, Math.max(chunk.length - copyIndex, 0));
                  copyIndex += readResult;
                } while (readResult > 0);
                result = new String(chunk, StandardCharsets.UTF_8).replace("\u0000", "");
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }finally{
            deleteFile(filePath);
        }
        return "SCAN Result : " + result;
    }

    @GetMapping("/test")
    public String testEndpoint(@RequestParam("test") String testParam){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(downloadFileFromGCS(testParam)));){
            StringBuilder sb = new StringBuilder();
            String line;        
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return line;
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void writeFile2Local(InputStream inputStream, String filePath){
        try(OutputStream output= new FileOutputStream(tmpFileParentPath + filePath);){
            int c;
            while ((c = inputStream.read()) != -1){
                output.write(c);
            }           
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(String filePath){
        File file = new File(tmpFileParentPath + filePath);
        file.delete();
    }

    private InputStream downloadFileFromGCS(String filePath){
        BlobId blobId = BlobId.of("clamav-sample", filePath);
        Blob blob = storage.get(blobId);
        byte[] content = blob.getContent();
        return new ByteArrayInputStream(content);
    }

    private static byte[] asBytes(String s) {
        return s.getBytes(StandardCharsets.US_ASCII);
    }
}
