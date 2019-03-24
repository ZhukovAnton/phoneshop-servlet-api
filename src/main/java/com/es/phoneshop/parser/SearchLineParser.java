package com.es.phoneshop.parser;

import java.util.StringTokenizer;

public class SearchLineParser {
    private static volatile SearchLineParser instance;

    public static SearchLineParser getInstance() {
        SearchLineParser localInstance = instance;
        if (localInstance == null) {
            synchronized (SearchLineParser.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SearchLineParser();
                }
            }
        }
        return localInstance;
    }

    public String[] parseLine(String searchLine){
     //   return searchLine.split("\\s", 5); I don't know why, but split is not working
        StringTokenizer stringForParse = new StringTokenizer(searchLine, " ");
        int amountOfTokes = stringForParse.countTokens();
        String[] result = new String[amountOfTokes];

        for (int i = 0; i < amountOfTokes; ++i){
            result[i] = stringForParse.nextToken();
        }

        return result;
    }
}
