package com.example.parser;

import com.example.store.VariableStore;

public abstract class ASTNode {
    public abstract Long evaluate(VariableStore store) throws Exception;
}
