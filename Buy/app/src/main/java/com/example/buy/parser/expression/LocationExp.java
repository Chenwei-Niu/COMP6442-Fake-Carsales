package com.example.buy.parser.expression;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Locale;

public class LocationExp extends Exp{
    private String location;
    private JexlEngine engine = new Engine();

    public LocationExp(String location) {
        this.location = location;
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlScript script = engine.createScript("if(car.location.toLowerCase(locale).equals(location)){return true;}else{return false;}");
        context.set("location",location);
        context.set("locale", Locale.ROOT);

        if (script.execute(context).toString().equals("false")){
            return false;
        } else {
            return true;
        }
    }
}
