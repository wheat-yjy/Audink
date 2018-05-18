package com.jishaokang.model.vo;

import com.jishaokang.model.ClassifyBook;
import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Chapter;
import lombok.Data;

import java.util.ArrayList;

@Data
public class HomepageVO {

    private ArrayList<String> imageUrl;
    private ArrayList<Book> recommend;
    private ArrayList<ClassifyBook> ClassifyBooks;

}