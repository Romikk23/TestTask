package Main;

import Database.Database;
import Expression.Lexeme;
import Expression.LexemeBuffer;
import Expression.LexemeType;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static Expression.Lexeme.expr;
import static Expression.Lexeme.lexAnalyze;

public class Main {
//    expr : plusminus* EOF ;
//    plusminus: multdiv ( ( '+' | '-' ) multdiv )* ;
//    multdiv : factor ( ( '*' | '/' ) factor )* ;
//    factor : NUMBER | '(' expr ')' ;


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            menuPrint();
            int choise = scanner.nextInt();
            switch (choise){
                case 1:
                    menuCalculations();
                    continue;
                case 2:
                    try{
                        menuEdit();
                    } catch (RuntimeException e){
                        System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
                    }
                    continue;
                case 3:
                    try{
                        menuOperator();
                    } catch (RuntimeException e){
                        System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
                    }
                    continue;
                case 4:
                    Database.clear();
                    System.out.println("\nDatabase has been cleared");
                    continue;
                default:
                    return;
            }
        }
    }
    public static double calculate(String expression){
        double result;
        int count = 0;
        List<Lexeme> lexemes = lexAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        try{
            result = expr(lexemeBuffer);
        } catch (RuntimeException e){
            throw e;
        }

        for(Lexeme lexeme : lexemes){
            if(lexeme.type == LexemeType.NUMBER){
                count++;
            }
        }
        System.out.printf("\nIn expression %d number(s)", count);
        return result;
    }
    public static void menuPrint() {
        System.out.println("\n\n    Main menu: \n");
        System.out.println(" 1.   Сalculate the expression");
        System.out.println(" 2.   Edit expression in database");
        System.out.println(" 3.   Find expression results from database with parameters");
        System.out.println(" 4.   Clear database");
        System.out.println(" 0.   Exit\n");

        System.out.print(" Make your choise: ");
    }

    public static void menuCalculations() throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPrint the expression: ");
        String expression = scanner.nextLine();
        double result;
        try{
            result = calculate(expression);
        } catch (RuntimeException e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
            return;
        }
        Database.addExpression(expression, String.valueOf(result));
        System.out.println("\n"+expression + " = " + result);
    }
    public static void menuEdit() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> expressions = Database.getExpressions();
        for (int i = 0; i < expressions.size(); i++)
            System.out.printf("\n [%d]: %s", i, expressions.get(i));
        System.out.print("\n\nPrint index:");
        int index = scanner.nextInt();

        if (index > expressions.size()-1) {
            throw new RuntimeException("Index out of bounds for length");
        }
        scanner.nextLine();

        String oldExpression = expressions.get(index).substring(0, expressions.get(index).indexOf('=')-1);
        System.out.print("Print expression:");
        String expression = scanner.nextLine();
        double result;
        try{
            result = calculate(expression);
        } catch (RuntimeException e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
            return;
        }
        Database.updateExpression(oldExpression, expression, String.valueOf(result));
        System.out.println("\n"+expression + " = " + result);
    }

    public static void menuOperator() throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPrint operator ( '<', '>', '=', '>=', '<=' ) :");
        String operator = scanner.nextLine();
        if(operator.equals("<")  || operator.equals(">") || operator.equals("=") || operator.equals("<=") || operator.equals(">=")) {
            System.out.print("Print number:");
            String number = scanner.nextLine();
            try{
                Double.parseDouble(number);
            } catch (NumberFormatException e){
                throw new RuntimeException("Unexpected number: " + number);
            }
            List<String> expressions = Database.getExpressionsWithParamether(operator, number);
            System.out.println(" Expressions where result " + operator + " " + number);
            for (int i = 0; i < expressions.size(); i++) {
                System.out.printf("\n [%d]: %s", i, expressions.get(i));
            }
        } else {
            throw new RuntimeException("Unknow operator");
        }

    }
}
