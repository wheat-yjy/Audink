package com.jishaokang.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by NANA_Final on 2018/1/29.
 */
public class PythonUtil {

    public static String runDealText(String codePath, String uploadpath) throws IOException, InterruptedException {
        Connection connection = Jsoup.connect("http://localhost:8000/audink/python/txt")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Accept", "text/plain, */*; q=0.01")
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "es-ES,es;q=0.8")
                .header("Connection", "keep-alive")
                .header("X-Requested-With", "XMLHttpRequest")
                .data("txtPosition",uploadpath+"/upload.txt")
                .data("xmlPosition",uploadpath+"/upload.xml")
                .maxBodySize(100)
                .timeout(1000 * 10000)
                .method(Connection.Method.POST);
        Connection.Response response = connection.execute();
        return uploadpath;
    }

    public static String runDealAudio(String codePath, String uploadpath) throws IOException, InterruptedException {
        Connection connection = Jsoup.connect("http://bluetata.com/")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Accept", "text/plain, */*; q=0.01")
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "es-ES,es;q=0.8")
                .header("Connection", "keep-alive")
                .header("X-Requested-With", "XMLHttpRequest")
                .data("audioPosition",uploadpath+"/upload.mp3")
                .maxBodySize(100)
                .timeout(1000 * 1000)
                .method(Connection.Method.POST);
        Connection.Response response = connection.execute();
        return uploadpath;
    }

    public static String getRecommend(int userId) throws IOException {
        String jsonBody = "{\"userId\":\""+userId+"\"}";
        System.out.println(jsonBody);
        Connection connection = Jsoup.connect("http://bluetata.com/")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
                .referrer("http://bluetata.com/")
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Accept", "text/plain, */*; q=0.01")
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .header("Accept-Language", "es-ES,es;q=0.8")
                .header("Connection", "keep-alive")
                .header("X-Requested-With", "XMLHttpRequest")
                .data("userId",""+userId)
                .requestBody(jsonBody)
                .maxBodySize(100)
                .timeout(1000 * 10)
                .method(Connection.Method.POST);
        Connection.Response response = connection.execute();
        String books = response.body();
        return books;
    }

}