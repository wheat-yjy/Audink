package com.jishaokang.service;


import com.jishaokang.base.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by NANA_Final on 2017/7/21.
 */

public interface DealService {

    Result dealText(String text, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException;

    Result dealTextFile(MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException;

    Result dealAudio(MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException, InterruptedException;
}