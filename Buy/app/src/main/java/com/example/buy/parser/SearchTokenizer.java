package com.example.buy.parser;

import java.util.Locale;

public class SearchTokenizer implements Tokenizer{
    // String to be transformed into tokens
    private String buffer;

    private int index;

    //The current token, will be updated when next() is called
    private Token currentToken;

    static final char[] whiteSpaces = {' ', '\n', '\t'};
    String[] validTokens = {"bmw","kia","mercedes-benz","ford","honda","kia","mazda","subaru",
    "vic","act","nsw","qld","nt","sa","wa","tas","sedan","coupe","suv","ute","convertible"
            ,"hatch","automatic","manual"};

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

    @Override
    public void next() {
        consumeWhite();     // remove whitespace
        buffer = buffer.toLowerCase(Locale.ROOT);    // make all alphabet lower case
        if (buffer.isEmpty() && index >= buffer.length()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int length;
        while (index<buffer.length() && buffer.indexOf(index)!=';'){
            stringBuilder.append(buffer.indexOf(index++));
        }
        length = stringBuilder.length();
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
                    throw new IllegalTokenException(stringBuilder.toString());
                } else if (stringBuilder.substring(0,6).equals("brand=")) {
                    currentToken= new Token(stringBuilder.substring(6),Token.Type.BRAND);
                } else if (stringBuilder.substring(0,8).equals("odometer")) {
                    currentToken= new Token(stringBuilder.substring(8),Token.Type.ODOMETER);
                } else {
                    if (length < 11) {
                        // the left tokens have minimum startup length of 11, which is
                        // "location=wa", otherwise, this is an invalid token
                        throw new IllegalTokenException(stringBuilder.toString());
                    }else {
                        if (stringBuilder.substring(0,9).equals("location=")) {
                            currentToken= new Token(stringBuilder.substring(10),Token.Type.LOCATION);
                        } else if (stringBuilder.substring(0,10).equals("bodystyle=")) {
                            currentToken= new Token(stringBuilder.substring(11),Token.Type.BODYSTYLE);
                        } else {
                            if (length < 19) {
                                // the left tokens have minimum startup length of 9, which is
                                // "transmission=manual", otherwise, this is an invalid token
                                throw new IllegalTokenException(stringBuilder.toString());
                            } else {
                                if (stringBuilder.substring(0,13).equals("transmission=")) {
                                    currentToken= new Token(stringBuilder.substring(8),Token.Type.ODOMETER);
                                }
                            }
                        }
                    }
                }
            }
        }
        // judge whether the currentToken is valid
        if (currentToken.getToken().length() <= 1){ //only one character, must be an invalid token
            throw new IllegalTokenException(stringBuilder.toString());
        } else {
            if (currentToken.getToken().equals(validComparator[3]) || currentToken.getToken().equals(validComparator[4])){
                // the current token is "<=" or ">=", but without number
                throw new IllegalTokenException(stringBuilder.toString());
            }
//            if (currentToken.getToken().length() >= 3 &&
//                    (currentToken.getToken().substring(0,2).equals(validComparator[3])
//                            || currentToken.getToken().substring(0,2).equals(validComparator[4]))){
//
//            }
        }

        if(index<buffer.length()){
            index++; // to skip the current ";"
        }






//        char firstChar = buffer.charAt(0);
//        if (firstChar == '+')
//            currentToken = new Token("+", Token.Type.ADD);
//        if (firstChar == '-')
//            currentToken = new Token("-", Token.Type.SUB);
//
//        String validTokens = "1234567890+-*/!()";
//        if (firstChar == '*')
//            currentToken = new Token("*", Token.Type.MUL);
//        if (firstChar == '/')
//            currentToken = new Token("/", Token.Type.DIV);
//        if (firstChar == '(')
//            currentToken = new Token("(", Token.Type.LBRA);
//        if (firstChar == ')')
//            currentToken = new Token(")", Token.Type.RBRA);
//        if (firstChar == '!')
//            currentToken = new Token("!", Token.Type.FAC);
//        if (Character.isDigit(firstChar)){
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(firstChar);
//            int length = buffer.length();
//            int i=1;
//            while (i<length && Character.isDigit(buffer.charAt(i))){
//                stringBuilder.append(buffer.charAt(i++));
//            }
//            currentToken = new Token(stringBuilder.toString(), Token.Type.INT);
//        }
//        if (!validTokens.contains(String.valueOf(firstChar))){
//            throw new Token.IllegalTokenException(String.valueOf(firstChar));
//        }
//
//
//        // ########## YOUR CODE ENDS HERE ##########
//        // Remove the extracted token from buffer
//        int tokenLen = currentToken.getToken().length();
//        buffer = buffer.substring(tokenLen);
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
