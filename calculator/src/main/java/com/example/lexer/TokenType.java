package com.example.lexer;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public enum TokenType {
    NUMBER("\\d+"),
    IDENTIFIER("[a-zA-Z_]\\w*"),
    PLUS("\\+"),
    MINUS("\\-"),
    MULTIPLY("\\*"),
    DIVIDE("/"),
    ASSIGN("="),
    PLUS_ASSIGN("\\+="),
    MINUS_ASSIGN("\\-="),
    MULTIPLY_ASSIGN("\\*="),
    DIVIDE_ASSIGN("/="),
    INCREMENT("\\+\\+"),
    DECREMENT("\\-\\-"),
    LEFT_PAREN("\\("),
    RIGHT_PAREN("\\)");
    
    private final Pattern compiledPattern;

    TokenType(String regex) {
        this.compiledPattern =
                regex.isEmpty() ? null : Pattern.compile("^" + regex + "$");
    }

    public boolean matches(String str) {
        return compiledPattern != null && compiledPattern.matcher(str).matches();
    }

    public static Optional<TokenType> fromString(String match) {
        return Arrays.stream(TokenType.values())
            .filter(type -> type.matches(match))
            .findFirst();
    }
}
