package com.jishaokang.service;


import com.jishaokang.base.Result;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Chapter;
import com.jishaokang.model.dto.Collect;
import com.jishaokang.model.dto.User;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by NANA_Final on 2017/7/21.
 */

public interface BookService {


    Result getBook(Collect collect);

    Result uploadBook(Book book,HttpSession session);

    Result uploadChapter(Chapter chapter);

    Result searchBook(String bookname);

    Result getBookByClassify(int userId, String classify) throws IOException;
}