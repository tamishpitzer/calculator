package com.example.parser;

import java.util.List;

import com.example.lexer.Token;
import com.example.lexer.TokenType;

public class Parser {
    private final List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    public ASTNode parse() throws ParserException {
        ASTNode expr = parseAssignment();
        if (!isAtEnd()) {
            throw new ParserException("Unexpected token after expression: " + peek().getValue());
        }
        return expr;
    }

    private ASTNode parseAssignment() throws ParserException {
        ASTNode expr = parseAdditive();

        if (matchAssignmentOp()) {
            TokenType op = previous().getType();
            if (!(expr instanceof VariableNode)) {
                throw new ParserException("Assignment target must be a variable");
            }
            ASTNode value = parseAssignment();
            VariableNode varNode = (VariableNode) expr;
            return new AssignmentNode(varNode.getName(), op, value);
        }

        return expr;
    }

    private ASTNode parseAdditive() throws ParserException {
        ASTNode expr = parseMultiplicative();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType op = previous().getType();
            ASTNode right = parseMultiplicative();
            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    private ASTNode parseMultiplicative() throws ParserException {
        ASTNode expr = parseUnary();

        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            TokenType op = previous().getType();
            ASTNode right = parseUnary();
            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    private ASTNode parseUnary() throws ParserException {
        // Prefix increment/decrement
        if (match(TokenType.INCREMENT, TokenType.DECREMENT)) {
            TokenType op = previous().getType();
            ASTNode operand = parseUnary();
            return new UnaryOpNode(op, operand, true);
        }

        return parsePostfix();
    }

    private ASTNode parsePostfix() throws ParserException {
        ASTNode expr = parsePrimary();

        // Postfix increment/decrement
        if (match(TokenType.INCREMENT, TokenType.DECREMENT)) {
            TokenType op = previous().getType();
            return new UnaryOpNode(op, expr, false);
        }

        return expr;
    }

    private ASTNode parsePrimary() throws ParserException {
        if (match(TokenType.NUMBER)) {
            return new LiteralNode(Long.parseLong(previous().getValue()));
        }

        if (match(TokenType.IDENTIFIER)) {
            return new VariableNode(previous().getValue());
        }

        if (match(TokenType.LEFT_PAREN)) {
            ASTNode expr = parseAssignment();
            if (!match(TokenType.RIGHT_PAREN)) {
                throw new ParserException("Expected ')' after expression");
            }
            return expr;
        }

        throw new ParserException("Unexpected token: " + peek().getValue());
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean matchAssignmentOp() {
        return match(TokenType.ASSIGN, TokenType.PLUS_ASSIGN, TokenType.MINUS_ASSIGN,
                TokenType.MULTIPLY_ASSIGN, TokenType.DIVIDE_ASSIGN);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    public static class ParserException extends Exception {
        public ParserException(String message) {
            super(message);
        }
    }
}
