package com.example.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
        "(\\d+)" +                    // Numbers
        "|([a-zA-Z_]\\w*)" +          // Identifiers
        "|(\\+\\+|--)" +              // increment/decrement
        "|(\\+=|-=|\\*=|/=)" +        // Compound assignments
        "|([+\\-*/()=])"              // Single char operators
    );

    private final String input;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() throws Exception {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();

            Token token = createToken(match);
            if (token != null) {
                tokens.add(token);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token createToken(String match) throws Exception {
              // TokenType type = TokenType.fromString(match).orElse(null);

        TokenType type = TokenType.fromString(match);
        if (type != null) {
            return new Token(type, match);
        }
        throw new Exception("Unrecognized token: " + match);
    }
}
