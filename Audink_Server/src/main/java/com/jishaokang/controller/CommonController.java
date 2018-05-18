package com.jishaokang.controller;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.User;
import com.jishaokang.service.BookService;
import com.jishaokang.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping(value = "/app")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/all/classify",method = RequestMethod.POST)
    public Result getBookByClassify(int userId,String classify) throws IOException {
        return bookService.getBookByClassify(userId,classify);
    }

    @RequestMapping(value = "/all/homepage",method = RequestMethod.POST)
    public Result getHomepage(User user) throws IOException {
        return commonService.getHomepage(user);
    }

}