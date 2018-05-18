package com.jishaokang.controller;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Chapter;
import com.jishaokang.model.dto.Collect;
import com.jishaokang.model.dto.User;
import com.jishaokang.service.BookService;
import com.jishaokang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;


@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "app/all/book",method = RequestMethod.POST)
    public Result getBook(Collect collect) {
        return bookService.getBook(collect);
    }

    @RequestMapping(value = "api/user/book/upload",method = RequestMethod.POST)
    public Result uploadBook(Book book,HttpSession session) {
        return bookService.uploadBook(book,session);
    }

    @RequestMapping(value = "api/user/chapter/upload",method = RequestMethod.POST)
    public Result uploadChapter(Chapter chapter) {
        return bookService.uploadChapter(chapter);
    }

    @RequestMapping(value = "app/all/search",method = RequestMethod.POST)
    public Result searchBook(String bookname) {
        return bookService.searchBook(bookname);
    }

}