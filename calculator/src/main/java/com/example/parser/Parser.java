package com.example.parser;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lexer.Token;
import com.example.lexer.TokenType;

@Service
public class Parser {
    private final List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    public ASTNode parse() throws Exception {

        ASTNode expr = parseAssignment();
        if (!isAtEnd()) {
            throw new Exception("Unexpected token after expression: " + peek().getValue());
        }
        return expr;
    }

    private ASTNode parseAssignment() throws Exception {
        ASTNode expr = parseAdditive();

        if (matchAssignmentOp()) {
            TokenType op = previous().getType();
            if (!(expr instanceof VariableNode)) {
                throw new Exception("Assignment target must be a variable");
            }
            ASTNode value = parseAssignment();
            VariableNode varNode = (VariableNode) expr;
            return new AssignmentNode(varNode.getName(), op, value);
        }

        return expr;
    }

    private ASTNode parseAdditive() throws Exception {
        ASTNode expr = parseMultiplicative();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType op = previous().getType();
            ASTNode right = parseMultiplicative();
            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    private ASTNode parseMultiplicative() throws Exception {
        ASTNode expr = parseUnary();

        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            TokenType op = previous().getType();
            ASTNode right = parseUnary();
            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    private ASTNode parseUnary() throws Exception {
        if (match(TokenType.INCREMENT, TokenType.DECREMENT)) {
            TokenType op = previous().getType();
            ASTNode operand = parsePostfix();
            return new UnaryOpNode(op, operand, true);
        }

        return parsePostfix();
    }

    private ASTNode parsePostfix() throws Exception {
        ASTNode expr = parsePrimary();

        if (match(TokenType.INCREMENT, TokenType.DECREMENT)) {
            TokenType op = previous().getType();
            return new UnaryOpNode(op, expr, false);
        }

        return expr;
    }

    private ASTNode parsePrimary() throws Exception {
        if (match(TokenType.NUMBER)) {
            return new LiteralNode(Long.parseLong(previous().getValue()));
        }

        if (match(TokenType.IDENTIFIER)) {
            return new VariableNode(previous().getValue());
        }

        if (match(TokenType.LEFT_PAREN)) {
            ASTNode expr = parseAssignment();
            if (!match(TokenType.RIGHT_PAREN)) {
                throw new Exception("Expected ')' after expression");
            }
            return expr;
        }

        throw new Exception("Unexpected token: " + peek().getValue());
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
        return tokens.size() == current;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}
