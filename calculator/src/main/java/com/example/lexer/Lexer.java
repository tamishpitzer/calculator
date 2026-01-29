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
        TokenType type = TokenType.fromString(match);
        if (type != null) {
            return new Token(type, match);
        }
        throw new LexerException("Unrecognized token: " + match);
    }

    public static class LexerException extends Exception {
        public LexerException(String message) {
            super(message);
        }
    }
}
