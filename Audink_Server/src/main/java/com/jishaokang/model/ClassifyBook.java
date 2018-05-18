package com.jishaokang.model;

import com.jishaokang.model.dto.Book;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by NANA_Final on 2018/5/13.
 */
@Data
public class ClassifyBook {
    private String classify;
    private ArrayList<Book> books;

    public ClassifyBook(String classify, ArrayList<Book> books) {
        this.classify = classify;
        this.books = books;
    }
}
