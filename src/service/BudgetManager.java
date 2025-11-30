package service;

import model.Expense;

import java.util.ArrayList;

/**
 * Manages a collection of expenses with CRUD operations.
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
            System.out.print((i + 1) + ". ");
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

    /**
     * Find all expenses by category name
     *
     * @param category expense category name, cannot be null
     * @throws IllegalArgumentException if category name is null
     * @return ArrayList with Expenses by category name
     */
    public ArrayList<Expense> findByCategory(String category){

        if(category == null){
            throw new IllegalArgumentException("Provide category name!");
        }

        ArrayList<Expense> expensesByCategory = new ArrayList<>();

        for(Expense expense : expenses){
            if(expense.getCategory().equals(category)){
                expensesByCategory.add(expense);
            }
        }

        return  expensesByCategory;
    }

    /**
     * Calculates total amount for specified category.
     *
     * @param category the category to calculate
     * @return total amount in PLN for that category
     */
    public double getTotalByCategory(String category){
        if(category == null){
            throw new IllegalArgumentException("Provide category name!");
        }

        ArrayList<Expense> expensesByCategory = findByCategory(category);
        double total = 0;

        for(Expense expense : expensesByCategory){
            total += expense.getAmount();
        }

        return total;
    }

    /**
     * Removes expense at specified index.
     *
     * @param index the index of expense to remove (0-based)
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void  removeExpense(int index){
         if(index < 0 || index >= expenses.size()){
             throw new IndexOutOfBoundsException("Invalid index");
         }

         expenses.remove(index);
        System.out.println("Expense with index: " + index + " removed.");
    }

    /**
     * Finds all expenses above specified amount.
     *
     * @param amount the minimum threshold
     * @throws IllegalArgumentException amount cannot be negative
     * @return ArrayList of expenses with amount > threshold
     */
    public ArrayList<Expense> findExpensesAbove(double amount){

        if(amount < 0){
            throw new IllegalArgumentException("Amount must be positive!");
        }

        ArrayList<Expense> expensesAboveAmount = new ArrayList<>();

        for(Expense expense : expenses){
            if(expense.getAmount() >= amount){
                expensesAboveAmount.add(expense);
            }
        }

        return  expensesAboveAmount;
    }
}
