package service;

import model.Expense;

import java.util.ArrayList;

/**
 * Mamages a collection of expenses with CRUD operations.
 *
 * This service class provides business logic for expense management
 * including adding, removing, displaying, and calculation statistics.
 * Uses ArrayList for dynamic storage.
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */

public class BudgetManager {

    private ArrayList<Expense> expenses;

    /**
     * Constructs a new BudgetManager with empty expene list.
     */
    public BudgetManager(){
        this.expenses = new ArrayList<>();
        System.out.println("Budget Manager initialized");
    }

    /**
     * Adds a new expense to the budget.
     *
     * @param expense the expense to add, must not be null
     * @throws IllegalArgumentException if expense is null
     */
    public void addExpense(Expense expense){
        if(expense == null){
            throw new IllegalArgumentException("Expense canot be null");
        }

        expenses.add(expense);
        System.out.println("✓ Added: " + expense.getName());
    }

    /**
     * Displays all expenses in the budget.
     * Shows numbered list with formatted expense infromation.
     */
    public void displayAllExpenses(){
        if(expenses.isEmpty()){
            System.out.println("No expenses to display");
            return;
        }

        System.out.println("\n=== All Expenses ===");
        for(int i=0; i<expenses.size(); i++){
            System.out.println((i + 1) + ". ");
            expenses.get(i).displayInfo();
        }
    }

    /**
     * Calculates the total amount of all expenses.
     *
     * @return sum of all expense amounts in PLN
     */
    public double calculateTotal(){
        double total = 0;
        for(Expense expense : expenses){
            total += expense.getAmount();
        }

        return  total;
    }

    /**
     * Returns the number of expenses in the budget.
     *
     * @return count of expenses
     */
    public int getExpenseCount(){
        return expenses.size();
    }

    /**
     * Find the most expensive expense in the budget
     *
     * @return the expense with highest amount, or null if no expenses
     */
    public Expense findMostExpensive(){
        if(expenses.isEmpty()){
            return  null;
        }

        Expense mostExpensive = expenses.get(0);

        for(Expense expense : expenses){
            if(expense.getAmount() > mostExpensive.getAmount()){
                mostExpensive = expense;
            }
        }

        return mostExpensive;
    }

    /**
     * Finds the least expensive expense in the budget.
     *
     * @return the expense with lowest amount, or null if no expenses
     */
    public Expense findCheapest(){
        if(expenses.isEmpty()){
            return  null;
        }

        Expense cheapest = expenses.get(0);

        for(Expense expense : expenses){
            if(expense.getAmount() < cheapest.getAmount()){
                cheapest = expense;
            }
        }

        return  cheapest;
    }

    /**
     * Clears all expenses from the budget.
     * This operation cannot be undone.
     */
    public void clearAllExpenses(){
        expenses.clear();
        System.out.println("All expenses cleared");
    }
}
