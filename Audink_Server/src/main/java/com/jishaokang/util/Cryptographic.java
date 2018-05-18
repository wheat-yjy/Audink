package com.jishaokang.util;

/**
 * Created by Administrator on 2017/4/5.
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Cryptographic {

    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
