package com.example;

import java.util.Scanner;

import com.example.evaluator.Evaluator;

/**
 * Text-based Calculator Application
 * 
 * Evaluates assignment expressions with operators:
 * - Arithmetic: +, -, *, /
 * - Assignment: =, +=, -=, *=, /=
 * - Increment/Decrement: ++, --
 * 
 * Example:
 *   i = 0
 *   j = ++i
 *   x = i++ + 5
 *   y = (5 + 3) * 10
 *   i += y
 * Output: (i=82,j=1,x=6,y=80)
 */
public class App {
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Text-based Calculator. Enter expressions (Ctrl+D or Ctrl+Z to finish):");
        System.out.println();

        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    evaluator.evaluateExpression(line);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }

            System.out.println();
            System.out.println("Result: " + evaluator.getVariableStore());
        } finally {
            scanner.close();
        }
    }
}
