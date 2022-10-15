package com.example.buy.parser;

import static org.junit.Assert.*;

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

}