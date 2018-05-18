package com.jishaokang.model.vo;

import com.jishaokang.model.dto.Book;
import com.jishaokang.model.dto.Chapter;
import lombok.Data;

import java.util.ArrayList;

@Data
public class BookVO {

    private Integer bookId;
    private String uploader;
    private String author;
    private String imageUrl;
    private String bookname;
    private String classify;
    private String summary;

    private ArrayList<Chapter>chapters;

    public BookVO(Book book){
        this.author = book.getAuthor();
        this.bookId = book.getBookId();
        this.bookname = book.getBookname();
        this.classify = book.getClassify();
        this.imageUrl = book.getImageUrl();
        this.summary = book.getSummary();
        this.uploader = book.getUploader();
    }
}