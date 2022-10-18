package com.example.buy.parser.expression;
//Author: Chenwei Niu
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Locale;

public class TransmissionExp extends Exp{
    private String transmission;
    private JexlEngine engine = new Engine();

    public TransmissionExp(String transmission) {
        this.transmission = transmission;
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlScript script = engine.createScript("if(car.transmission.toLowerCase(locale).equals(transmission)){return true;}else{return false;}");
        context.set("transmission",transmission);
        context.set("locale", Locale.ROOT);
        if (script.execute(context).toString().equals("false")){
            return false;
        } else {
            return true;
        }
    }
}
