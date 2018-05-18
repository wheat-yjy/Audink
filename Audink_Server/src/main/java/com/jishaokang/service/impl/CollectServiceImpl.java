package com.jishaokang.service.impl;

import com.jishaokang.base.Result;
import com.jishaokang.cache.ResultCache;
import com.jishaokang.dao.BookDAO;
import com.jishaokang.dao.ChapterDAO;
import com.jishaokang.dao.CollectDAO;
import com.jishaokang.dao.UserDAO;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Collect;
import com.jishaokang.model.vo.CollectVO;
import com.jishaokang.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by NANA_Final on 2018/1/28.
 */
@Service

public class CollectServiceImpl implements CollectService {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private CollectDAO collectDAO;

    @Override
    public Result getCollect(Integer userId) {
        CollectVO collectVO = new CollectVO();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Collect> collects = collectDAO.selectByUserId(userId);
        for (Collect collect : collects){
            books.add(bookDAO.select(collect.getBookId()));
        }
        collectVO.setBooks(books);
        return ResultCache.getDataOk(collectVO);
    }

    @Override
    public Result addCollect(Collect collect) {
        collectDAO.insert(collect);
        return ResultCache.OK;
    }

    @Override
    public Result deleteCollect(Collect collect) {
        collectDAO.delete(collect);
        return ResultCache.OK;
    }
}