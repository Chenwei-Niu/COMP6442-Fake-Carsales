package com.example.buy.parser.expression;
//Author: Chenwei Niu
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
            comparator="==";
            price = price.substring(1);
        } else if (price.charAt(0) == '>' && price.charAt(1) == '='){
            comparator=">=";
            price = price.substring(2);
        }else if (price.charAt(0) == '<' && price.charAt(1) == '='){
            comparator="<=";
            price = price.substring(2);
        } else {
            comparator = price.substring(0,1);
            price = price.substring(1);
        }

    }

    public String getComparator() {
        return comparator;
    }

    public String getPrice() {
        return price;
    }
}
