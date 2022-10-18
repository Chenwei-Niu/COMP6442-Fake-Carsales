package com.example.buy.parser;
//Author: Chenwei Niu
import com.example.buy.entity.Car;
import com.example.buy.entity.Market;
import com.example.buy.parser.expression.BodyStyleExp;
import com.example.buy.parser.expression.BrandExp;
import com.example.buy.parser.expression.Exp;
import com.example.buy.parser.expression.LocationExp;
import com.example.buy.parser.expression.OdometerExp;
import com.example.buy.parser.expression.PriceExp;
import com.example.buy.parser.expression.TransmissionExp;
import com.example.buy.parser.expression.YearExp;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.ArrayList;

public class Parser {

    private JexlEngine engine = new Engine();
    private JexlContext context = new MapContext();

    private SearchTokenizer searchTokenizer;
    private ArrayList<Exp> queryAttributes = new ArrayList<>();
    private ArrayList searchResultList = new ArrayList<>();

    public Parser(SearchTokenizer searchTokenizer) {
        this.searchTokenizer = searchTokenizer;
    }

    public void parseExp(){
        if (!searchTokenizer.hasNext()) {
            return;
        } else if (searchTokenizer.hasNext() && searchTokenizer.current().getType()== Token.Type.YEAR){
            queryAttributes.add(new YearExp(searchTokenizer.current().getToken()));
            searchTokenizer.next();
            parseExp();
        } else if (searchTokenizer.hasNext() && searchTokenizer.current().getType()== Token.Type.BRAND){
            queryAttributes.add(new BrandExp(searchTokenizer.current().getToken()));
            searchTokenizer.next();
            parseExp();
        } else if (searchTokenizer.hasNext() && searchTokenizer.current().getType()== Token.Type.PRICE){
            queryAttributes.add(new PriceExp(searchTokenizer.current().getToken()));
            searchTokenizer.next();
            parseExp();
        } else if (searchTokenizer.hasNext() && searchTokenizer.current().getType()== Token.Type.ODOMETER){
            queryAttributes.add(new OdometerExp(searchTokenizer.current().getToken()));
            searchTokenizer.next();
            parseExp();
        } else if (searchTokenizer.hasNext() && searchTokenizer.current().getType()== Token.Type.LOCATION){
            queryAttributes.add(new LocationExp(searchTokenizer.current().getToken()));
            searchTokenizer.next();
            parseExp();
        } else if (searchTokenizer.hasNext() && searchTokenizer.current().getType()== Token.Type.BODYSTYLE){
            queryAttributes.add(new BodyStyleExp(searchTokenizer.current().getToken()));
            searchTokenizer.next();
            parseExp();
        } else if (searchTokenizer.hasNext() && searchTokenizer.current().getType()== Token.Type.TRANSMISSION){
            queryAttributes.add(new TransmissionExp(searchTokenizer.current().getToken()));
            searchTokenizer.next();
            parseExp();
        }
    }

    public ArrayList<Exp> getQueryAttributes() {
        return queryAttributes;
    }

    public ArrayList<Car> executeQuery(){

        for (int i = 0; i< Market.getMarket().getCarArray().size(); i++){
            context.set("car",Market.getMarket().getCarArray().get(i));
            int flag = 1; // means satisfy all requirements
            for (Exp query : queryAttributes){
                if (!query.evaluate(context)){
                    flag = 0;
                    break;
                }
            }
            if(flag==1){
                searchResultList.add(Market.getMarket().getCarArray().get(i));
            }
        }
        System.out.println(searchResultList);
        return searchResultList;
    }
}
