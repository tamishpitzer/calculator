package com.example.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.lexer.Token;
import com.example.lexer.TokenType;

public class LexerUtil {
    private LexerUtil() {}

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
        "(\\d+)" +                    // Numbers
        "|([a-zA-Z_]\\w*)" +          // Identifiers
        "|(\\+\\+|--)" +              // increment/decrement
        "|(\\+=|-=|\\*=|/=)" +        // Compound assignments
        "|([+\\-*/()=])"              // Single char operators
    );
    
    public static List<Token> tokenize(String input) throws Exception {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();
            tokens.add(createToken(match));
        }

        return tokens;
    }

    private static Token createToken(String match) throws Exception {
        return TokenType.fromString(match)
               .map(type -> new Token(type, match))
               .orElseThrow(() -> new Exception("Unrecognized token: " + match));
    }
}
