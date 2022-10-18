package com.example.buy.parser;
//Author: Chenwei Niu

import org.junit.Test;

public class MyJsonReaderTest {
    @Test
    public void testParseOneLine(){
        MyJsonReader myJsonReader = new MyJsonReader();
        myJsonReader.parseOneLine("{\"information\": \"2019 Kia Cerato Sport Auto MY19\", \"price\": \"27270\", \"image\": \"https://carsales.pxcrush.net/carsales//cars/dealer/1r24lq33kgjxcsvip7kv69cmu.jpg?pxc_method=gravityfill&amp;pxc_bgtype=self&amp;pxc_size=720,480\", \"odometer\": \"30351\", \"location\": \"VIC\", \"bodyStyle\": \"Hatch\", \"transmission\": \"Automatic\", \"engine\": \"4cyl 2.0L Petrol\", \"state\": \"ONSALE\", \"brand\": \"kia\"}");
    }
}
