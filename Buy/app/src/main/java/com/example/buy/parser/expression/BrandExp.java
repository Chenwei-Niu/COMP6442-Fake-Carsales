package com.example.buy.parser.expression;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

public class BrandExp extends Exp{
    private String brand;
    private JexlEngine engine = new Engine();

    public BrandExp(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlScript script = engine.createScript("if(car.brand.equals(brand)){return true;}else{return false;}");
        context.set("brand",brand);
        if (script.execute(context).toString().equals("false")){
            return false;
        } else {
            return true;
        }
    }
}
