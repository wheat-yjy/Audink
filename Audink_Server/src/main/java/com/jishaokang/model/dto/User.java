package com.jishaokang.model.dto;

import lombok.Data;

@Data
public class User {

    /*用户ID*/
    private Integer userId;
    /*用户名*/
    private String username;
    /*密码*/
    private String password;

}