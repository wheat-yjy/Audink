package com.jishaokang.nlp.util;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static com.jishaokang.nlp.util.QuoteUtil.inQuote;

/**
 * Created by NANA_Final on 2018/1/27.
 */
public class ExpressVerbUtil {

    private static Set<String> EXPRESS_VERBS = new HashSet<String>();

    public static void init(String path) {
        String file = path + "/ExpressVerbs.txt";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
            String str;
            while ((str = br.readLine()) != null) {
                EXPRESS_VERBS.add(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean IsExpressVerb(String verb){
        for (int i=0;i<verb.length();i++){
            if (inQuote(verb,i) && EXPRESS_VERBS.contains(verb.substring(i,i+1))) return true;
        }
        return false;
    }


}