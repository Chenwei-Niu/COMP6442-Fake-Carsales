package com.example.buy.parser.expression;
//Author: Chenwei Niu
import org.apache.commons.jexl3.JexlContext;

public abstract class Exp {
    // The reason for returning String is that we will use
    // commons-jexl3 in org.apache.commons to dynamically execute the String code
    public abstract boolean evaluate(JexlContext context);
}