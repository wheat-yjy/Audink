package com.jishaokang.base;

import lombok.Data;

@Data
public class Result {

    private Integer status;
    private String message;
    private Object data = null;

    public Result(int status,String message){
        this.status = status;
        this.message = message;
    }

    public Result(int status,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
