package com.jishaokang.controller;


import com.jishaokang.base.Result;
import com.jishaokang.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@RestController
@RequestMapping(value = "/api/user")
public class DealController {

    @Autowired
    DealService dealService;

    @RequestMapping(value = "/dealText",method = RequestMethod.POST)
    public Result dealText(String text, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException {
        return dealService.dealText(text,request,session);
    }

    @RequestMapping(value = "/dealTextFile",method = RequestMethod.POST)
    public Result dealTextFile(@RequestParam(value = "text", required = false) MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException {
        return dealService.dealTextFile(file,request,session);
    }

    @RequestMapping(value = "/dealAudioFile",method = RequestMethod.POST)
    public Result dealAudio(@RequestParam(value = "audio", required = false) MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException {
        return dealService.dealAudio(file,request,session);
    }

}