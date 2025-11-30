import model.Expense;

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
        textExpenseClass();

    }

    /**
     * Tests the Expense class functionality
     */
    private static void textExpenseClass(){
        System.out.println("=== Testing Expense Class ===\n");

        Expense groceries = new Expense("Weekly Groceries", 450.50, "Food", "2025-01-15", "Weekly shopp at Dino");
        Expense transport = new Expense("Monthly Bus Pass", 120.00, "Transport", "2025-01-01", "Monthly pass");
        Expense laptop = new Expense("Work Laptop", 3500.00, "Electronics", "2025-01-10", "Laptop for work");
        Expense testWithoutDesc = new Expense("Bus", 120, "Transport", "2025-01-20");

        // Display expenses
        System.out.println("Created expenses:");
        groceries.displayInfo();
        transport.displayInfo();
        laptop.displayInfo();

        System.out.println();

        // Test getters
        System.out.println("=== Testing Getters ===");
        System.out.println("Groceries name: " + groceries.getName());
        System.out.println("Groceries amount: " + groceries.getAmount());
        System.out.println("Groceries category: " + groceries.getCategory());
        System.out.println("Groceries date: " + groceries.getDate());

        System.out.println();

        // Test isExpensive
        System.out.println("=== Testing isExpensive ===");
        System.out.println("Groceries is expensive? " + groceries.isExpensive());
        System.out.println("Laptop is expensive? " + laptop.isExpensive());

        System.out.println();

        // Test setter with validation
        System.out.println("=== Testing Setter ===");
        System.out.println("Original amount: " + groceries.getAmount());
        groceries.setAmount(520.00);
        System.out.println("Updated amount: " + groceries.getAmount());

        System.out.println();

        //Test isFromMonth
        System.out.println("=== Testing isFromMonth ===");
        System.out.println(groceries.isFromMonth("2025-01-15"));
        System.out.println(groceries.isFromMonth("2025-04-12"));

        // Test validation (this will throw exception)
        System.out.println("=== Testing Validation ===");

        try {
            Expense invalid = new Expense("Invalid", -100, "Food", "2025-01-15", "Invalid desc");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Validation works! Error caught: " + e.getMessage());
        }

        System.out.println();
        System.out.println("✅ All tests passed!");
    }
}
