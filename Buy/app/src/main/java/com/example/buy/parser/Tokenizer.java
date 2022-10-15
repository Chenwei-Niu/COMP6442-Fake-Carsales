package com.example.buy.parser;

public interface Tokenizer {
    public boolean hasNext(); // Check whether tokenizer still has tokens left
    public Token current(); // Return the current token
    public void next(); // extract the next token from the input buffer
                        // and update the current token

}
