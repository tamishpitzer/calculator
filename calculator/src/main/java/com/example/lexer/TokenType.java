package com.example.lexer;

public enum TokenType {
    // Literals and identifiers
    NUMBER,
    IDENTIFIER,

    // Arithmetic operators
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,

    // Assignment operators
    ASSIGN,
    PLUS_ASSIGN,
    MINUS_ASSIGN,
    MULTIPLY_ASSIGN,
    DIVIDE_ASSIGN,

    // Increment/Decrement
    INCREMENT,
    DECREMENT,

    // Parentheses
    LEFT_PAREN,
    RIGHT_PAREN,

    // Special
    EOF
}
