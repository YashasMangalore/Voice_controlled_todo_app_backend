package com.todo.voice.util;

import java.util.Set;

public class StemmerHelper {
    public static String getPlaceholders(String text, Set<String> protectedWords){
        for(String term:protectedWords){
            if(text.contains(term)){
                text=text.replace(term,"_PLACEHOLDER_");
            }
        }
        return text;
    }
}
