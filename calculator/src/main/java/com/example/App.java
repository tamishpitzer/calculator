package com.example;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.evaluator.Evaluator;


@SpringBootApplication
public class App {
    public static void main(String[] args) {
        
        ApplicationContext context = SpringApplication.run(App.class, args);

        Evaluator evaluator = context.getBean(Evaluator.class);
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
