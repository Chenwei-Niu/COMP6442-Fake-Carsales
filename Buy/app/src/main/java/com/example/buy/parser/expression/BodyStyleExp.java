package com.example.buy.parser.expression;
//Author: Chenwei Niu
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Locale;

public class BodyStyleExp extends Exp{
    private String bodyStyle;
    private JexlEngine engine = new Engine();

    public BodyStyleExp(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    @Override
    public boolean evaluate(JexlContext context) {
        JexlScript script = engine.createScript("if(car.bodyStyle.toLowerCase(locale).replace(\" \",\"\").equals(bodyStyle)){return true;}else{return false;}");
        context.set("bodyStyle",bodyStyle);
        context.set("locale", Locale.ROOT);
        if (script.execute(context).toString().equals("false")){
            return false;
        } else {
            return true;
        }
    }
}
