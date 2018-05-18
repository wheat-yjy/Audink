package com.jishaokang.dao;

import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.User;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by NANA_Final on 2017/7/21.
 */
@Repository
public interface BookDAO {


    Book select(Integer bookId);

    ArrayList<Book> selectByClassify(String classify);

    void insert(Book book);

    ArrayList<Book> selectByName(String bookname);

    ArrayList<Book> selectByClassifyLimit(String classify);
}