package com.jishaokang.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by NANA_Final on 2018/5/17.
 */
public class test2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Connection connection = Jsoup.connect("http://localhost:8080/app/all/book")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
                .data("userId","1")
                .data("bookId","6")
                .method(Connection.Method.POST);
        Connection.Response response = connection.execute();
        System.out.println(response);
        
    }
}
