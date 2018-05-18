package com.jishaokang.service.impl;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.dao.BookDAO;
import com.jishaokang.dao.ChapterDAO;
import com.jishaokang.dao.CollectDAO;
import com.jishaokang.dao.UserDAO;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Chapter;
import com.jishaokang.model.dto.Collect;
import com.jishaokang.model.dto.User;
import com.jishaokang.model.vo.BookVO;
import com.jishaokang.service.BookService;
import com.jishaokang.service.UserService;
import com.jishaokang.util.Cryptographic;
import com.jishaokang.util.PythonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by NANA_Final on 2018/1/28.
 */
@Service

public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CollectDAO collectDAO;


    @Override
    public Result getBook(Collect collect) {
        System.out.println(collect);
        Book book = bookDAO.select(collect.getBookId());
        ArrayList<Chapter> chapters = chapterDAO.selectByBookId(collect.getBookId());
        BookVO bookVO = new BookVO(book);
        bookVO.setChapters(chapters);
        collectDAO.insertView(collect);
        return ResultCache.getDataOk(bookVO);
    }

    @Override
    public Result getBookByClassify(int userId,String classify) throws IOException {
        ArrayList<Book> books= null;
        if (classify.equals("推荐")) {
            String[] bookList = PythonUtil.getRecommend(userId).split(",");
            books = new ArrayList<>();
            for (int i = 0;i<bookList.length;i++)
                books.add(bookDAO.select(Integer.parseInt(bookList[i])));
        }else books = bookDAO.selectByClassify(classify);
        return ResultCache.getDataOk(books);
    }

    @Override
    public Result uploadBook(Book book,HttpSession session) {
        int userId =(Integer) session.getAttribute("userId");
        User user = userDAO.selectByUserId(userId);
        book.setUploader(user.getUsername());
        bookDAO.insert(book);
        return ResultCache.getDataOk(book.getBookId());
    }

    @Override
    public Result uploadChapter(Chapter chapter) {
        chapterDAO.insert(chapter);
        return ResultCache.OK;
    }

    @Override
    public Result searchBook(String bookname) {
        System.out.println(bookname);
        ArrayList<Book> books = bookDAO.selectByName("%"+bookname+"%");
        return ResultCache.getDataOk(books);
    }

}