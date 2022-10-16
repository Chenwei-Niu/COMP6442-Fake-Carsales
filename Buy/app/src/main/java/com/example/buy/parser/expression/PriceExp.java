package com.example.buy.parser.expression;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

public class PriceExp extends Exp{
    private String comparator = "";
    private String price;

    public PriceExp(String price) {
        this.price = price;
        setComparator();
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlEngine engine = new Engine();
        JexlScript script = engine.createScript("if(car.price"+comparator+price+"){return true;}else{return false;}");
        if (script.execute(context).toString().equals("false")){
            return false;
        } else {
            return true;
        }
    }

    private void setComparator(){
        // if typed in "=", change it to "=="
        // if ">" "<" ">=" "<=" then comparator = "";
        if (price.charAt(0) == '='){
            comparator="=";
        }

    }
}
