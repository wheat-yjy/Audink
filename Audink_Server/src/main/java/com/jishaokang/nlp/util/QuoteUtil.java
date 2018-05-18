package com.jishaokang.nlp.util;

/**
 * Created by NANA_Final on 2018/2/3.
 */
public class QuoteUtil {

    public static boolean inQuote(String content,int startNum){
        int openQuote = content.indexOf('“');
        int closeQuote = content.indexOf('”');
        boolean inQuote = true;
        if (openQuote >= 0 && closeQuote >= 0 && openQuote > closeQuote) inQuote = false;
        if (openQuote < 0 && closeQuote >= 0) inQuote = false;
                for (int i=0;i<startNum;i++){
            if (content.charAt(i) == '“') inQuote = false;
            else if (content.charAt(i) == '”') inQuote = true;
        }
        //System.out.println(content+"  "+startNum+" : "+inQuote);
        return inQuote;
    }

}