package com.itheima.controller;

import com.itheima.utils.UploadUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 包名:com.itheima.controller
 *
 * @author Leevi
 * 日期2020-08-15  09:53
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/upload")
    public String upload(String pdesc, MultipartFile upload){
        System.out.println("pdesc="+pdesc);
        //1. 获得文件名
        String filename = upload.getOriginalFilename();
        //2. 获得随机文件名
        String uuidName = UploadUtils.getUUIDName(filename);

        //3. 使用跨服务器文件上传的方式，将客户端上传的文件写入到文件服务器中
        Client client = Client.create();
        //连接文件服务器
        WebResource resource = client.resource("http://localhost:8833/file/"+uuidName);
        //通过resource将客户端上传的文件写入到文件服务器
        try {
            resource.put(upload.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
