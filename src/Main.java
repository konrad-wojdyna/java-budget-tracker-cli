import model.Expense;
import service.BudgetManager;

import java.util.ArrayList;

/**
 * Budget Tracker CLI - Main Entry Point
 *
 * A command-line budget tracking application demonstrating
 * Core Java proficiency and OOP principles
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */

public class Main {
    public static void main(String[] args){

        System.out.println("Java Budget Tracker CLI");

        //Test Expense class
        testBudgetManager();

    }

    /**
     * Tests BudgetManager functionality with ArrayList
     */
    public static void testBudgetManager(){
        System.out.println("=== Testing BudgetManager ===\n");

        BudgetManager manager = new BudgetManager();

        System.out.println();

        //Add expenses
        System.out.println("--- Adding Expenses ---");
        manager.addExpense(new Expense("Weekly Groceries", 450.50, "Food", "2025-01-15"));
        manager.addExpense(new Expense("Monthly Bus Pass", 120.00, "Transport", "2025-01-01"));
        manager.addExpense(new Expense("Netflix Subscription", 49.00, "Entertainment", "2025-01-05"));
        manager.addExpense(new Expense("Work Laptop", 3500.00, "Electronics", "2025-01-10"));
        manager.addExpense(new Expense("Coffee", 15.50, "Food", "2025-01-16"));

        System.out.println();

        //Display all
        manager.displayAllExpenses();

        System.out.println();

        // Statistics
        System.out.println("=== Statistics ===");
        System.out.println("Total expenses: " + manager.getExpenseCount());
        System.out.println("Total amount: " + String.format("%.2f", manager.calculateTotal()) + " PLN");

        System.out.println();

        // Find most expensive
        Expense mostExpensive = manager.findMostExpensive();
        if (mostExpensive != null) {
            System.out.println("Most expensive:");
            System.out.print("  ");
            mostExpensive.displayInfo();
        }

        System.out.println();

        // Find cheapest
        Expense cheapest = manager.findCheapest();
        if (cheapest != null) {
            System.out.println("Cheapest:");
            System.out.print("  ");
            cheapest.displayInfo();
        }

        System.out.println();

        System.out.println("=== Test find by category ===");
        ArrayList<Expense> foodExpenses = manager.findByCategory("Food");
        System.out.println("Food expenses: " + foodExpenses.size());
        for(Expense expense : foodExpenses){
            expense.displayInfo();
        }

        System.out.println();

        System.out.println("=== Test calculate total by category ===");
        double foodTotal = manager.getTotalByCategory("Food");
        System.out.println("Food total: " + String.format("%.2f", foodTotal) + " PLN");

        System.out.println();

        System.out.println("=== Test remove expense by index ===");
        System.out.println("Before remove: " + manager.getExpenseCount());
        manager.removeExpense(2);
        System.out.println("After remove: " + manager.getExpenseCount());
        manager.displayAllExpenses();

        System.out.println();

        System.out.println("=== Test expense above amount ===");
        ArrayList<Expense> expensive = manager.findExpensesAbove(100);
        System.out.println("Expenses above 100 PLN: " + expensive.size());


        System.out.println();
        System.out.println("✅ All tests passed!");
    }
}

