package com.example.buy.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {

    @Test
    public void parseExp() {
        SearchTokenizer tokenizer = new SearchTokenizer("year>=2017");
        Parser parser = new Parser(tokenizer);
        parser.parseExp();
        System.out.println(parser.getQueryAttributes().toString());
    }
}