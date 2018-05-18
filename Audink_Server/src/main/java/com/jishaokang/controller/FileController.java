package com.jishaokang.controller;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.util.IPUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/5.
 */
@RestController
public class FileController {

    public static final String KEYSTRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int len = KEYSTRING.length();

    @RequestMapping(value = "/api/all/util/uploadPicture",method = RequestMethod.POST)
    public Result updateFile(@RequestParam(value = "img", required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println(file);
        if (file == null) return ResultCache.getDataOk("http://"+IPUtil.getIP()+":8080"+"/audink/upload/img/pic.jpg");
        Random random = new Random();
        StringBuilder sb = new StringBuilder(new Date().getTime()+"");
        for (int i = 0;i < 50;i++ )
            sb = sb.append(KEYSTRING.charAt(random.nextInt(len)));
        sb.append(".");
        sb.append(file.getOriginalFilename().split("\\.")[1]);
        String path = request.getServletContext().getRealPath("/upload/img/");
        String fileName = sb.toString();
        File targetFile = new File(path, fileName);
        System.out.println(path+"   "+fileName);
        targetFile.createNewFile();
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultCache.getDataOk("http://"+IPUtil.getIP()+":8080"+"/audink/upload/img/"+fileName);
    }

}