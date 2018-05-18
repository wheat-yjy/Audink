package com.jishaokang.service.impl;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.dao.BookDAO;
import com.jishaokang.dao.ChapterDAO;
import com.jishaokang.model.ClassifyBook;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Chapter;
import com.jishaokang.model.dto.User;
import com.jishaokang.model.vo.BookVO;
import com.jishaokang.model.vo.HomepageVO;
import com.jishaokang.service.BookService;
import com.jishaokang.service.CommonService;
import com.jishaokang.util.PythonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NANA_Final on 2018/1/28.
 */
@Service

public class CommonServiceImpl implements CommonService {

    @Autowired
    private BookDAO bookDAO;

    @Override
    public Result getHomepage(User user) throws IOException {
        ArrayList<String> imageUrl = new ArrayList<>();
        imageUrl.add("http://120.24.70.191:8080/audinkapp/upload/recommend/1.jpg");
        imageUrl.add("http://120.24.70.191:8080/audinkapp/upload/recommend/2.jpg");
        imageUrl.add("http://120.24.70.191:8080/audinkapp/upload/recommend/3.jpg");
        HomepageVO homepageVO = new HomepageVO();
        homepageVO.setImageUrl(imageUrl);
        ArrayList books = new ArrayList<>();
        books.add(bookDAO.select(18));
        books.add(bookDAO.select(19));
        books.add(bookDAO.select(20));
        homepageVO.setRecommend(books);
        ArrayList<ClassifyBook> classifyBooks = new ArrayList<>();

        //String[] bookList = PythonUtil.getRecommend(user.getUserId()).split(",");
        //ArrayList<Book> books = new ArrayList<>();
        //for (int i = 0;i<bookList.length;i++) books.add(bookDAO.select(Integer.parseInt(bookList[i])));
        //classifyBooks.add(new ClassifyBook("推荐",books));
        classifyBooks.add(new ClassifyBook("经典",bookDAO.selectByClassifyLimit("经典")));
        classifyBooks.add(new ClassifyBook("言情",bookDAO.selectByClassifyLimit("言情")));
        classifyBooks.add(new ClassifyBook("都市",bookDAO.selectByClassifyLimit("都市")));
        classifyBooks.add(new ClassifyBook("玄幻",bookDAO.selectByClassifyLimit("玄幻")));
        homepageVO.setClassifyBooks(classifyBooks);
        return ResultCache.getDataOk(homepageVO);
    }

}