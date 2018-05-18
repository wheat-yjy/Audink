package com.jishaokang.nlp.model;

import lombok.Data;

/**
 * Created by NANA_Final on 2018/1/26.
 */
@Data
public class Pronoun {

    private String name;
    private Integer tokenIndex;

    public Pronoun(String name, Integer tokenIndex) {
        this.name = name;
        this.tokenIndex = tokenIndex;
    }
}
