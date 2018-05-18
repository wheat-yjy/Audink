package com.jishaokang.service.impl;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.dao.ChapterDAO;
import com.jishaokang.model.dto.Record;
import com.jishaokang.nlp.util.ReplacePronouns;
import com.jishaokang.service.DealService;
import com.jishaokang.service.RecordService;
import com.jishaokang.util.FileUtil;
import com.jishaokang.util.IPUtil;
import com.jishaokang.util.PythonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NANA_Final on 2018/1/28.
 */
@Service
public class DealServiceImpl implements DealService {

    @Autowired
    RecordService recordService;
    @Autowired
    ReplacePronouns replacePronouns;


    @Override
    public Result dealText(String text, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException {
        int userId =(Integer) session.getAttribute("userId");
        Record record = new Record();
        recordService.addRecord(record,session);
        String servletPath = request.getServletContext().getRealPath("/upload").replaceAll("\\\\","/");
        String uploadPath = servletPath + "/" + userId + "/" + record.getRecordId();
        FileUtil.updateText(uploadPath,text);
        replacePronouns.replacePronouns(uploadPath+"/upload.txt",servletPath,uploadPath);
        PythonUtil.runDealText(servletPath, uploadPath);
        String fileName = "/audink/upload/" + userId + "/" + record.getRecordId() + "/output.mp3";
        String summary = FileUtil.readFile(uploadPath + "/output.txt");
        Map map = new HashMap();
        map.put("filename","http://"+IPUtil.getIP()+":8080"+fileName);
        map.put("summary",summary);
        map.put("content",text);
        return ResultCache.getDataOk(map);
    }

    @Override
    public Result dealTextFile(MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException {
        int userId =(Integer) session.getAttribute("userId");
        Record record = new Record();
        recordService.addRecord(record,session);
        String servletPath = request.getServletContext().getRealPath("/upload").replaceAll("\\\\","/");
        String uploadPath = servletPath + "/" + userId + "/" + record.getRecordId();
        FileUtil.updateTextFile(uploadPath,file);
        replacePronouns.replacePronouns(uploadPath+"/upload.txt",servletPath,uploadPath);
        PythonUtil.runDealText(servletPath + "/python",uploadPath);
        String fileName = "/audink/upload/" + userId + "/" + record.getRecordId() + "/output.mp3";
        String summary = FileUtil.readFile(uploadPath + "/output.txt");
        Map map = new HashMap();
        map.put("filename","http://"+IPUtil.getIP()+":8080"+fileName);
        map.put("summary",summary);
        map.put("content",FileUtil.readFile(fileName));
        return ResultCache.getDataOk(map);
    }

    @Override
    public Result dealAudio(MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException {
        int userId =(Integer) session.getAttribute("userId");
        Record record = new Record();
        recordService.addRecord(record,session);
        String servletPath = request.getServletContext().getRealPath("/upload").replaceAll("\\\\","/");
        String uploadPath = servletPath + "/" + userId + "/" + record.getRecordId();
        FileUtil.updateAudio(uploadPath,file);
        PythonUtil.runDealAudio(servletPath + "/python", uploadPath);
        String fileName = "http://"+IPUtil.getIP()+":8080"+"/audink/upload/" + userId + "/" + record.getRecordId() + "/output.wav";
        return ResultCache.getDataOk(fileName);
    }

}