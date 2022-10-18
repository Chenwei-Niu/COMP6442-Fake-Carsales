package com.example.buy.parser.expression;
//Author: Chenwei Niu
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Locale;

public class BrandExp extends Exp{
    private String brand;
    private JexlEngine engine = new Engine();

    public BrandExp(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlScript script = engine.createScript("if(car.brand.toLowerCase(locale).equals(brand)){return true;}else{return false;}");
        context.set("brand",brand);
        context.set("locale", Locale.ROOT);
        if (script.execute(context).toString().equals("false")){
            return false;
        } else {
            return true;
        }
    }
    public String getBrand() {
        return brand;
    }
}
