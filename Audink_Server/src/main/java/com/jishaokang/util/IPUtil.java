package com.jishaokang.util;

import java.net.*;
import java.util.Enumeration;

/**
 * Created by NANA_Final on 2018/5/18.
 */
public class IPUtil {
    public static String getIP(){
        Enumeration<NetworkInterface> allNetInterfaces = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (allNetInterfaces.hasMoreElements()){
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress ip = (InetAddress) addresses.nextElement();
                if (ip != null
                        && ip instanceof Inet4Address
                        && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                        && ip.getHostAddress().indexOf(":")==-1){
                    return ip.getHostAddress();
                }
            }
        }
        return null;
    }
}
