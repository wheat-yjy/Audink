package com.jishaokang.test;

import com.jishaokang.util.IPUtil;
import com.jishaokang.util.PythonUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by NANA_Final on 2018/5/17.
 */
public class test {
    public static void main(String[] args) throws IOException, InterruptedException {

//        Connection connection = Jsoup.connect("http://192.168.0.125:8000/audink/python/txt")
//                .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36") // User-Agent of Chrome 55
//                .header("Content-Type", "application/json; charset=UTF-8")
//                .header("Accept", "text/plain, */*; q=0.01")
//                .header("Accept-Encoding", "gzip,deflate,sdch")
//                .header("Accept-Language", "es-ES,es;q=0.8")
//                .header("Connection", "keep-alive")
//                .header("X-Requested-With", "XMLHttpRequest")
//                .data("txtPosition","/home/embedded/test")
//                .data("xmlPosition","/home/embedded/tt.xml")
//                .method(Connection.Method.POST)
//                .maxBodySize(100)
//                .timeout(1000 * 1000);
//        Connection.Response response = connection.execute();
//        System.out.println(response);
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()){
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress ip = (InetAddress) addresses.nextElement();
                if (ip != null
                        && ip instanceof Inet4Address
                        && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                        && ip.getHostAddress().indexOf(":")==-1){
                    System.out.println(ip.getHostAddress());
                }
            }
        }
    }
}