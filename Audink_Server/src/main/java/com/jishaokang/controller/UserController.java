package com.jishaokang.controller;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.User;
import com.jishaokang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping(value = "/app")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all/login",method = RequestMethod.POST)
    public Result login(User user) throws NoSuchAlgorithmException {
        return userService.login(user);
    }

    @RequestMapping(value = "/all/register",method = RequestMethod.POST)
    public Result register(User user) throws NoSuchAlgorithmException {
        return userService.register(user);
    }

    @RequestMapping(value = "/user/getUserName",method = RequestMethod.POST)
    public Result getUserName(User user){
        return userService.getUserName(user);
    }

}