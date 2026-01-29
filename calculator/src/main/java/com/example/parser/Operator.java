package com.example.parser;

import java.util.HashMap;
import java.util.Map;

import com.example.lexer.TokenType;

public enum Operator {
    PLUS((a, b) -> a + b),
    MINUS((a, b) -> a - b),
    MULTIPLY((a, b) -> a * b),
    DIVIDE((a, b) -> {
        if (b == 0) throw new RuntimeException("Division by zero");
        return a / b;
    });

    @FunctionalInterface
    public interface BinaryOp {
        long apply(long a, long b);
    }

    private final BinaryOp operation;

    Operator(BinaryOp operation) {
        this.operation = operation;
    }

    public long apply(long left, long right) {
        return operation.apply(left, right);
    }

    // Single source of truth for all mappings
    private static final Map<TokenType, Operator> TOKEN_TO_OP = new HashMap<>();

    static {
        TOKEN_TO_OP.put(TokenType.PLUS, PLUS);
        TOKEN_TO_OP.put(TokenType.PLUS_ASSIGN, PLUS);
        
        TOKEN_TO_OP.put(TokenType.MINUS, MINUS);
        TOKEN_TO_OP.put(TokenType.MINUS_ASSIGN, MINUS);
        
        TOKEN_TO_OP.put(TokenType.MULTIPLY, MULTIPLY);
        TOKEN_TO_OP.put(TokenType.MULTIPLY_ASSIGN, MULTIPLY);
        
        TOKEN_TO_OP.put(TokenType.DIVIDE, DIVIDE);
        TOKEN_TO_OP.put(TokenType.DIVIDE_ASSIGN, DIVIDE);
    }

    public static Operator from(TokenType type) {
        return TOKEN_TO_OP.get(type);
    }
}

