package com.seecen.springboot.controller;

import ch.qos.logback.core.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 鎻忚堪:
 *
 * @author ZhangJiankun
 * @create 2019-12-12 11:22
 */
@Controller
public class FileController {
    //
    private  static  final Logger LOGGER=
            LoggerFactory.getLogger(FileController.class);

    @Value("${uploadpath}")
    private String uploadpath;

    @Value("${vpath}")
    private String vpath;

    @GetMapping("/toUpload")
    public String toUpload(){
        return "upload.html";
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest request, ModelMap map){
        //
        String filename = file.getOriginalFilename();
        //
        File disFile=new File(uploadpath,filename);
        //
        if(!disFile.getParentFile().exists()){
            disFile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(disFile);
        } catch (IOException e) {
            //
            LOGGER.error("文件上传异常",e);
        }
        map.put("fileName",filename);
        map.put("filePath",vpath+filename);// /uploadFile/a.jpg
        return "fileInfo.html";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(String fileName) throws IOException {
        //创建文件对象
        File file = new File(uploadpath,fileName);//E:upload/xx.jpg
        //构建响应头
        HttpHeaders headers = new HttpHeaders();
        //设置以附件形式打开
        headers.setContentDispositionFormData("attachment",
                new String(fileName.getBytes("UTF-8"),
                        "iso-8859-1"));
        //设置文档类型为stream
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(
                FileCopyUtils.copyToByteArray(file),//文件字节数组
                headers,//头部信息
                HttpStatus.CREATED);//http状态码
    }

}
