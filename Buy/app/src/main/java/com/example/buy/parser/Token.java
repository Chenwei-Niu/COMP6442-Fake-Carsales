package com.example.buy.parser;
//Author: Chenwei Niu
import java.util.Objects;

/**
 * @feature search
 * @author Chenwei Niu
 */
public class Token {
    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    // The following enum defines different types of token,
    // which will be used in search functionality
    public enum Type {YEAR, PRICE,ODOMETER,LOCATION,BODYSTYLE,TRANSMISSION,BRAND}

    public String getToken(){
        return this.token;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return getToken().equals(token1.getToken()) && getType() == token1.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getType());
    }
}
