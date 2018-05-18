package com.jishaokang.controller;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.User;
import com.jishaokang.service.UserService;
import com.jishaokang.service.UserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping(value = "/api")
public class UserWebController {

    @Autowired
    private UserWebService userService;

    @RequestMapping(value = "/all/login",method = RequestMethod.POST)
    public Result login(User user, HttpSession session, HttpServletResponse response) throws NoSuchAlgorithmException {
        return userService.login(user,session,response);
    }

    @RequestMapping(value = "/user/unlogin",method = RequestMethod.GET)
    public Result unlogin(HttpSession session,HttpServletResponse response) {
        return userService.unlogin(session,response);
    }

    @RequestMapping(value = "/all/register",method = RequestMethod.POST)
    public Result register(User user) throws NoSuchAlgorithmException {
        return userService.register(user);
    }

    @RequestMapping(value = "/user/getUserName",method = RequestMethod.POST)
    public Result getUserName(HttpSession session) {
        return userService.getUserName(session);
    }

}