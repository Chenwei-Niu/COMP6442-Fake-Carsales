package com.example.buy.parser.expression;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;
/**
 * @feature search
 * @author Chenwei Niu
 */
public class YearExp extends Exp{
    private String comparator = "";
    private String year;

    public YearExp(String year) {
        this.year = year;
        setComparator();
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlEngine engine = new Engine();
        JexlScript script = engine.createScript("if(car.year"+comparator+year+"){return true;}else{return false;}");
        if (script.execute(context).toString().equals("false")){
            return false;
        } else {
            return true;
        }
    }

    private void setComparator(){
        // if typed in "=", change it to "=="
        // if ">" "<" ">=" "<=" then comparator = "";
        if (year.charAt(0) == '='){
            comparator="=";
        }

    }
}
