package com.example.lexer;

import java.util.regex.Pattern;

public enum TokenType {
    NUMBER("\\d+", null),
    IDENTIFIER("[a-zA-Z_]\\w*", null),

    PLUS("\\+", (a, b) -> a + b),
    MINUS("\\-", (a, b) -> a - b),
    MULTIPLY("\\*", (a, b) -> a * b),
    DIVIDE("/", (a, b) -> {
        if (b == 0) throw new RuntimeException("Division by zero");
        return a / b;
    }),

    ASSIGN("=", null),
    PLUS_ASSIGN("\\+=", (a, b) -> a + b),
    MINUS_ASSIGN("\\-=", (a, b) -> a - b),
    MULTIPLY_ASSIGN("\\*=", (a, b) -> a * b),
    DIVIDE_ASSIGN("/=", (a, b) -> {
        if (b == 0) throw new RuntimeException("Division by zero");
        return a / b;
    }),
    
    INCREMENT("\\+\\+", null),
    DECREMENT("\\-\\-", null),
    LEFT_PAREN("\\(", null),
    RIGHT_PAREN("\\)", null),
    EOF("", null);

    @FunctionalInterface
    public interface BinaryOp {
        long apply(long a, long b);
    }

    private final String pattern;
    private final Pattern compiledPattern;
    private final BinaryOp operation;

    TokenType(String pattern, BinaryOp operation) {
        this.pattern = pattern;
        this.operation = operation;
        this.compiledPattern = pattern.isEmpty() ? null : Pattern.compile("^" + pattern + "$");
    }

    public boolean matches(String str) {
        if (compiledPattern == null) return false;
        return compiledPattern.matcher(str).matches();
    }

    public BinaryOp getOperation() {
        return operation;
    }

    public static TokenType fromString(String match) {
        for (TokenType type : TokenType.values()) {
            if (type.matches(match)) {
                return type;
            }
        }
        return null;
    }
}


