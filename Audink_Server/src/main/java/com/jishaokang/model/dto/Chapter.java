package com.jishaokang.model.dto;

import lombok.Data;

@Data
public class Chapter {

    private Integer chapterId;
    private Integer bookId;
    private String content;
    private String chaptername;
    private String audioUrl;
    private String lrc;
    private String abst;

}