package com.example.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
        "(\\d+)" +                    // Numbers
        "|([a-zA-Z_]\\w*)" +          // Identifiers
        "|(\\+\\+|--)" +              // Pre/Post increment/decrement
        "|(\\+=|-=|\\*=|/=)" +        // Compound assignments
        "|(==)" +                     // Equality (for future use)
        "|([+\\-*/()=])" +            // Single char operators
        "|\\s+"                       // Whitespace (skip)
    );

    private final String input;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() throws LexerException {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();

            // Skip whitespace
            if (match.matches("\\s+")) {
                continue;
            }

            Token token = createToken(match);
            if (token != null) {
                tokens.add(token);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token createToken(String match) throws LexerException {
        if (match.matches("\\d+")) {
            return new Token(TokenType.NUMBER, match);
        } else if (match.matches("[a-zA-Z_]\\w*")) {
            return new Token(TokenType.IDENTIFIER, match);
        } else if (match.equals("++")) {
            return new Token(TokenType.INCREMENT, match);
        } else if (match.equals("--")) {
            return new Token(TokenType.DECREMENT, match);
        } else if (match.equals("+=")) {
            return new Token(TokenType.PLUS_ASSIGN, match);
        } else if (match.equals("-=")) {
            return new Token(TokenType.MINUS_ASSIGN, match);
        } else if (match.equals("*=")) {
            return new Token(TokenType.MULTIPLY_ASSIGN, match);
        } else if (match.equals("/=")) {
            return new Token(TokenType.DIVIDE_ASSIGN, match);
        } else if (match.equals("+")) {
            return new Token(TokenType.PLUS, match);
        } else if (match.equals("-")) {
            return new Token(TokenType.MINUS, match);
        } else if (match.equals("*")) {
            return new Token(TokenType.MULTIPLY, match);
        } else if (match.equals("/")) {
            return new Token(TokenType.DIVIDE, match);
        } else if (match.equals("(")) {
            return new Token(TokenType.LEFT_PAREN, match);
        } else if (match.equals(")")) {
            return new Token(TokenType.RIGHT_PAREN, match);
        } else if (match.equals("=")) {
            return new Token(TokenType.ASSIGN, match);
        }

        throw new LexerException("Unrecognized token: " + match);
    }

    public static class LexerException extends Exception {
        public LexerException(String message) {
            super(message);
        }
    }
}
