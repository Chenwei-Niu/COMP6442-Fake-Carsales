package com.example.buy.parser;

import org.junit.Test;

public class SearchTokenizerTest {


    @Test
    public void testConsumeWhite() {
        String testString = "  brand =   audi\t\n";
        SearchTokenizer searchTokenizer = new SearchTokenizer(testString);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("year=6");
        System.out.println(stringBuilder.substring(7));
    }

    @Test
    public void testSearchTokenizer() {
        String testString = "  brand =   bmw\t\n; ye";
        SearchTokenizer searchTokenizer = new SearchTokenizer(testString);
        System.out.println(searchTokenizer.current());
        searchTokenizer.next();
    }

}