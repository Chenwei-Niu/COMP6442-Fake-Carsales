package com.example.buy.parser;
//Author: Chenwei Niu
import com.example.buy.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * tokenizer
 * @feature search
 * @author Chenwei Niu
 */
public class SearchTokenizer implements Tokenizer{
    // String to be transformed into tokens
    private String buffer;

    private int index;

    //The current token, will be updated when next() is called
    private Token currentToken;

    static final char[] whiteSpaces = {' ', '\n', '\t'};
    String[] validTokens = {"bmw","kia","mercedes-benz","toyota","audi","kia","mazda","subaru",
    "vic","act","nsw","qld","nt","sa","wa","tas","sedan","coupe","suv","ute","peoplemover","convertible"
            ,"hatch","wagon","automatic","manual"};
    List<String> validTokensList = Arrays.asList(validTokens);

    String [] validComparator = {"<",">","=","<=",">="};

    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }


    public SearchTokenizer(String buffer) {
        this.buffer = buffer; // get the String from search bar
        next(); // extract the first token and store it in the currentToken
    }

    @Override
    public boolean hasNext() {
        return currentToken!=null;
    }

    @Override
    public Token current() {
        return currentToken;
    }

    /**
     * get the next valid token, throw Illegal token exceptions
     * if an invalid token or partial valid token is read.
     */
    @Override
    public void next() {
        consumeWhite();     // remove whitespace
        buffer = buffer.toLowerCase(Locale.ROOT);    // make all alphabet lower case
        if (buffer.isEmpty() || index >= buffer.length()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int length;
        while (index<buffer.length() && buffer.charAt(index)!=';'){
            stringBuilder.append(buffer.charAt(index++));
        }
        System.out.println(stringBuilder);
        length = stringBuilder.length();
        System.out.println("length of stringBuilder is "+length);
        if (length < 6){
            // the tokens have minimum startup length of 6, which is
            // "year>0"
            // if length less than 6, it's an invalid token
            throw new IllegalTokenException(stringBuilder.toString());
        } else {
            if (stringBuilder.substring(0,4).equals("year")) {
                currentToken= new Token(stringBuilder.substring(4),Token.Type.YEAR);
            } else if (stringBuilder.substring(0,5).equals("price")) {
                currentToken= new Token(stringBuilder.substring(5),Token.Type.PRICE);
            } else {
                if(length < 9){
                    // the left tokens have minimum startup length of 9, which is
                    // "brand=kia", otherwise, this is an invalid token
                    throw new IllegalTokenException(stringBuilder.toString() + "0");
                } else if (stringBuilder.substring(0,6).equals("brand=")) {
                    currentToken= new Token(stringBuilder.substring(6),Token.Type.BRAND);
                } else if (stringBuilder.substring(0,8).equals("odometer")) {
                    currentToken= new Token(stringBuilder.substring(8),Token.Type.ODOMETER);
                } else {
                    if (length < 11) {
                        // the left tokens have minimum startup length of 11, which is
                        // "location=wa", otherwise, this is an invalid token
                        throw new IllegalTokenException(stringBuilder.toString()+ "1");
                    }else {
                        if (stringBuilder.substring(0,9).equals("location=")) {
                            currentToken= new Token(stringBuilder.substring(9),Token.Type.LOCATION);
                        } else if (stringBuilder.substring(0,10).equals("bodystyle=")) {
                            currentToken= new Token(stringBuilder.substring(10),Token.Type.BODYSTYLE);
                        } else {
                            if (length < 19) {
                                // the left tokens have minimum startup length of 9, which is
                                // "transmission=manual", otherwise, this is an invalid token
                                System.out.println(123);
                                throw new IllegalTokenException(stringBuilder.toString()+ "2");
                            } else {
                                if (stringBuilder.substring(0,13).equals("transmission=")) {
                                    currentToken= new Token(stringBuilder.substring(13),Token.Type.TRANSMISSION);
                                } else {
                                    // there must be somewhere mistyped
                                    throw new IllegalTokenException(stringBuilder.toString()+ "3");
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(currentToken);
        System.out.println(currentToken.getToken());
        // judge whether the currentToken is valid
        if (currentToken.getToken().length() <= 1){ //only one character, must be an invalid token
            throw new IllegalTokenException(stringBuilder.toString());
        } else {
            if (currentToken.getToken().equals(validComparator[3]) || currentToken.getToken().equals(validComparator[4])){
                // the current token is "<=" or ">=", but without number
                throw new IllegalTokenException(stringBuilder.toString());

            }else if (currentToken.getToken().length() >= 3 &&
                    (currentToken.getToken().substring(0,2).equals(validComparator[3])
                            || currentToken.getToken().substring(0,2).equals(validComparator[4]))){
                if(!Utils.isANumber(currentToken.getToken().substring(2))){
                    // the current token starts at "<=" or ">=",
                    // but the following content is not number
                    throw new IllegalTokenException(stringBuilder.toString());
                }
            } else if (currentToken.getToken().substring(0,1).equals(validComparator[0])
                    || currentToken.getToken().substring(0,1).equals(validComparator[1])
                    || currentToken.getToken().substring(0,1).equals(validComparator[2])) {
                if (!Utils.isANumber(currentToken.getToken().substring(1))) {
                    // the current token starts at "<" or ">" or "=",
                    // but the following content is not number
                    throw new IllegalTokenException(stringBuilder.toString());
                }
            }else if (!validTokensList.contains(currentToken.getToken())){
                // the current token is a String, but not included in the validToken list
                throw new IllegalTokenException(stringBuilder.toString());
            }
        }

        if(index<buffer.length()){
            index++; // to skip the current ";"
        }


    }

    private void consumeWhite(){
        for (int i = 0; i<whiteSpaces.length;i++){
            buffer = buffer.replace(Character.toString(whiteSpaces[i]), "");
        }
    }

    public String getBuffer() {
        return buffer;
    }

    public int getIndex() {
        return index;
    }

    public Token getCurrentToken() {
        return currentToken;
    }
}
