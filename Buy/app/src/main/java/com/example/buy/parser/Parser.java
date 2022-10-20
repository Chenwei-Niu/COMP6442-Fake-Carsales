package com.example.buy.parser;

import com.example.buy.avltree.AvlNode;
import com.example.buy.avltree.AvlTree;
import com.example.buy.entity.ArrayListIterator;
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
import java.util.HashMap;

/**
 * Parse the token read by tokenizer
 * @author Chenwei Niu
 * @feature search functionality
 */
public class Parser {

    private JexlEngine engine = new Engine();
    private JexlContext context = new MapContext();

    private SearchTokenizer searchTokenizer;
    private ArrayList<Exp> queryAttributes = new ArrayList<>();
    private ArrayList<Car> carSourceList = new ArrayList<>();
    private ArrayList<Car> searchResultList = new ArrayList<>();

    public Parser(SearchTokenizer searchTokenizer) {
        this.searchTokenizer = searchTokenizer;
    }

    /**
     * main parseExp method, parse token into corresponding Exp
     * these Exp are stored in the queryAttribute List,
     * which would be used to query later
     * @author Chenwei Niu
     */
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

    /**
     * Execute the query based on the query attributes.
     * Different data structure would be used to search based on query attributes.
     * AVLTrees would be searched if brandExp or PriceExp is included in query attributes.
     * @author Chenwei Niu
     * @return
     */
    public ArrayList<Car> executeQuery(){
        // use AVLTree to query if we have brand/price attributes
        BrandExp brandExp = null;
        PriceExp priceExp = null;
        for (int i =0;i<queryAttributes.size();i++){
            if (queryAttributes.get(i) instanceof BrandExp){
                brandExp = (BrandExp) queryAttributes.get(i);
            }
            if (queryAttributes.get(i) instanceof PriceExp){
                priceExp = (PriceExp) queryAttributes.get(i);
            }
        }
        // if we both have brand and price attributes
        if (brandExp!=null && priceExp!=null){
            AvlTree brandTree = (AvlTree) Market.getMarket().getMap().get(brandExp.getBrand());
            Car tempCar = new Car();
            tempCar.setPrice(Integer.parseInt(priceExp.getPrice()));
            if (priceExp.getComparator().equals("==")){
                AvlNode<Car> resultNode = brandTree.find(brandTree.root,tempCar);
                if (resultNode != null){
                    carSourceList.add(resultNode.getElement());
                    if(!resultNode.getArrayList().isEmpty()){
                        for (Car car: resultNode.getArrayList()){
                            carSourceList.add(car);
                        }
                    }
                } else {
                    System.out.println("No such car satisfies your query");
                }
            } else if (priceExp.getComparator().equals(">=")) {
                brandTree.findGreaterOrEqualThan(brandTree.root,tempCar,carSourceList);
            } else if (priceExp.getComparator().equals(">")) {
                brandTree.findGreaterThan(brandTree.root,tempCar,carSourceList);
            } else if (priceExp.getComparator().equals("<=")) {
                brandTree.findLessOrEqualThan(brandTree.root,tempCar,carSourceList);
            } else if (priceExp.getComparator().equals("<")) {
                brandTree.findLessThan(brandTree.root,tempCar,carSourceList);
            }
        } else if (brandExp==null && priceExp!=null){
            //iterate each brandTree
            HashMap<String,AvlTree<Car>> map = Market.getMarket().getMap();
            Car tempCar = new Car();
            tempCar.setPrice(Integer.parseInt(priceExp.getPrice()));
            for (AvlTree avlTree: map.values()){
                if (priceExp.getComparator().equals("==")){
                    AvlNode<Car> resultNode = avlTree.find(avlTree.root,tempCar);
                    if (resultNode != null){
                        carSourceList.add(resultNode.getElement());
                        if(!resultNode.getArrayList().isEmpty()){
                            for (Car car: resultNode.getArrayList()){
                                carSourceList.add(car);
                            }
                        }
                    } else {
                        System.out.println("No such car satisfies your query");
                    }
                } else if (priceExp.getComparator().equals(">=")) {
                    avlTree.findGreaterOrEqualThan(avlTree.root,tempCar,carSourceList);
                } else if (priceExp.getComparator().equals(">")) {
                    avlTree.findGreaterThan(avlTree.root,tempCar,carSourceList);
                } else if (priceExp.getComparator().equals("<=")) {
                    avlTree.findLessOrEqualThan(avlTree.root,tempCar,carSourceList);
                } else if (priceExp.getComparator().equals("<")) {
                    avlTree.findLessThan(avlTree.root,tempCar,carSourceList);
                }
            }

        } else if (brandExp!=null && priceExp==null){

            // if only brand attribute is available
            AvlTree brandTree = (AvlTree) Market.getMarket().getMap().get(brandExp.getBrand());
            carSourceList = brandTree.toArrayList();
        } else {

            // if we do not have brand/price attributes, then use arraylist to query
            for (ArrayListIterator arrayListIterator = Market.getMarket().createIterator(); arrayListIterator.hasNext();){
                Car car = (Car) arrayListIterator.next();
                carSourceList.add(car);
            }
        }
            for (int i = 0; i< carSourceList.size(); i++){
                int flag = 1; // means satisfy all requirements
                context.set("car",carSourceList.get(i));
                for (Exp query : queryAttributes){
                    if (!query.evaluate(context)){
                        flag = 0;
                        break;
                    }
                }
                if(flag==1){
                    searchResultList.add(carSourceList.get(i));
                }
            }



        System.out.println(searchResultList);
        return searchResultList;
    }

}
