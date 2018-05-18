package com.jishaokang.model.dto;

import lombok.Data;

@Data
public class Book {

    private Integer bookId;
    private String uploader;
    private String author;
    private String imageUrl;
    private String bookname;
    private String classify;
    private String summary;

}