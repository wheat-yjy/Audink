package com.jishaokang.util;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.Random;

/**
 * Created by NANA_Final on 2018/1/28.
 */
public class FileUtil {


    public static String updateText(String uploadPath, String content) throws IOException {
        File path = new File(uploadPath);
        path.mkdirs();
        System.out.println(uploadPath);
        File file = new File(uploadPath, "upload.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        System.out.println(fileOutputStream);
        fileOutputStream.write(content.getBytes("UTF-8"));
        fileOutputStream.close();
        return uploadPath+"/upload.txt";
    }


    public static String updateTextFile(String uploadPath,MultipartFile file) throws IOException {
        File path = new File(uploadPath);
        path.mkdirs();
        File targetFile = new File(uploadPath, "upload.txt");
        if(!targetFile.exists()){
            targetFile.createNewFile();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadPath+"/upload.txt";
    }

    public static String updateAudio(String uploadPath,MultipartFile file) throws IOException {
        File path = new File(uploadPath);
        path.mkdirs();
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        File targetFile = new File(uploadPath, "upload."+type);
        targetFile.createNewFile();
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadPath+"/upload."+type;
    }

    public static String outputXML(String uploadPath,String content) throws IOException {
        File file = new File(uploadPath, "upload.xml");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        System.out.println(fileOutputStream);
        fileOutputStream.write(content.getBytes("UTF-8"));
        fileOutputStream.close();
        return uploadPath+"/upload.xml";
    }

    public static boolean deleteFile(String fileName, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/upload/");
        File file = new File(path, fileName);
        if (file.exists() && file.isFile())
            if (file.delete()) return true;
        return false;
    }

    public static String readFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        while ((str = br.readLine()) != null) {
            stringBuffer.append(str);
            stringBuffer.append("\r\n");
        }
        br.close();
        return stringBuffer.toString();
    }
}