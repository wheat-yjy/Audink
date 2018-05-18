package com.jishaokang.controller;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Collect;
import com.jishaokang.model.dto.User;
import com.jishaokang.service.BookService;
import com.jishaokang.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/app")
public class CollectController {
    
    @Autowired
    private CollectService collectService;

    @RequestMapping(value = "/user/collect/all",method = RequestMethod.POST)
    public Result getCollection(User user)  {
        return collectService.getCollect(user.getUserId());
    }

    @RequestMapping(value = "/user/collect/add",method = RequestMethod.POST)
    public Result getBook(Collect collect) {
        return collectService.addCollect(collect);
    }

    @RequestMapping(value = "/user/collection/delete",method = RequestMethod.POST)
    public Result getCollection(Collect collect)  {
        return collectService.deleteCollect(collect);
    }

}