package service;

import model.Category;
import model.Expense;
import model.Priority;
import repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of expenses with CRUD operations.
 * This service class provides business logic for expense management
 * including adding, removing, displaying, and calculation statistics.
 *
 * This calss depends on ExpenseRepository INTERFACE,
 * not a concrete implementation. This allows:
 * - Easy testing (mock repository)
 * - Flexible storage (memory, file, database)
 * - Loose coupling
 *
 *
 * @author Konrad Wojdyna
 * @version 0.5.0
 */

public class BudgetManager {

    private final ExpenseRepository repository;

    /**
     * Creates BudgetManager with specified repository.
     *
     * @param repository the expense repository implementation
     * @throws IllegalArgumentException if repository is null
     */
    public BudgetManager(ExpenseRepository repository){

        if(repository == null){
            throw new IllegalArgumentException("Repository cannot be null");
        }

        this.repository = repository;
        System.out.println("Budget Manager initialized with " +
                repository.getClass().getSimpleName());
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

        repository.save(expense);
        System.out.println("✓ Added: " + expense.getName());
    }

    /**
     * Creates and adds expense with all details.
     * Convenience method to avoid creating Expense object separately.
     *
     * @param name expense name
     * @param amount expense amount
     * @param category expense category
     * @param date expense date
     * @param description expense description
     * @param priority expense priority
     */
    public void addExpense(String name, double amount, Category category, String date, String description,
                           Priority priority){
        Expense expense = new Expense(name, amount, category, date, description, priority);
        addExpense(expense);
    }

    /**
     * Creates and adds expense without priority (defaults to MEDIUM).
     *
     * @param name expense name
     * @param amount expense amount
     * @param category expense category
     * @param date expense date
     * @param description expense description
     */
    public void addExpense(String name, double amount, Category category, String date, String description){
        addExpense(name, amount, category, date, description, Priority.MEDIUM);
    }

    /**
     * Creates and adds expense without description or priority.
     *
     * @param name expense name
     * @param amount expense amount
     * @param category expense category
     */
    public void addExpense(String name, double amount, Category category, String date){
        addExpense(name, amount, category, date, "", Priority.MEDIUM);
    }

    public void addExpense(Expense ...expenses){
        if(expenses == null || expenses.length == 0){
            System.out.println("No expenses provided");
            return;
        }

        repository.saveAll(expenses);
        System.out.println("✓ Bulk add complete expenses added.");
    }

    /**
     * Displays all expenses in the budget.
     * Shows numbered list with formatted expense infromation.
     */
    public void displayAllExpenses(){

        List<Expense> allExpenses = repository.findAll();

        if(allExpenses.isEmpty()){
            System.out.println("No expenses to display");
            return;
        }

        System.out.println("\n=== All Expenses ===");
        for(int i=0; i<allExpenses.size(); i++){
            System.out.print((i + 1) + ". ");
            allExpenses.get(i).displayInfo();
        }
    }

    /**
     * Calculates the total amount of all expenses.
     *
     * @return sum of all expense amounts in PLN
     */
    public double calculateTotal(){
        List<Expense> allExpenses = repository.findAll();

        double total = 0;
        for(Expense expense : allExpenses){
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
        return repository.count();
    }

    /**
     * Find the most expensive expense in the budget
     *
     * @return the expense with the highest amount, or null if no expenses
     */
    public Expense findMostExpensive(){

        List<Expense> allExpenses = repository.findAll();

        if(allExpenses.isEmpty()){
            return  null;
        }

        Expense mostExpensive = allExpenses.getFirst();

        for(Expense expense : allExpenses){
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

        List<Expense> allExpenses = repository.findAll();

        if(allExpenses.isEmpty()){
            return  null;
        }

        Expense cheapest = allExpenses.getFirst();

        for(Expense expense : allExpenses){
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
        repository.deleteAll();
        System.out.println("All expenses cleared");
    }

    /**
     * Find all expenses by category name
     *
     * @param category expense category enum
     * @throws IllegalArgumentException if category name is null
     */
    public List<Expense> findByCategory(Category category){
          return  repository.findByCategory(category);
    }

    /**
     * Calculates total amount for specified category.
     *
     * @param category the category enum to calculate
     * @return total amount in PLN for that category
     */
    public double getTotalByCategory(Category category){
        List<Expense> expensesByCategory = findByCategory(category);

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
        Expense expense = repository.findById(index);
        repository.delete(index);
        System.out.println("Removed: " + expense.getName());
    }

    /**
     * Finds all expenses above specified amount.
     *
     * @param amount the minimum threshold
     * @throws IllegalArgumentException amount cannot be negative
     * @return ArrayList of expenses with amount > threshold
     */
    public List<Expense> findExpensesAbove(double amount){
        return repository.findExpensesAbove(amount);
    }

    /**
     * Finds all expenses with specified priority.
     *
     * @param priority level
     * @return ArrayList of expenses with that priority
     */
    public List<Expense> findByPriority(Priority priority){
        return repository.findByPriority(priority);
    }

    /**
     * Dispalys statistics grouped by category.
     * Shows only categories with expenses.
     */
    public void displayCategoryStatistics(){
        System.out.println("\n=== Category Statistics ===");
        System.out.println("───────────────────────────────────────");

        Category[] categories = Category.values();
        boolean hasAny = false;

        for(Category category : categories){
            List<Expense> categoryExpense = findByCategory(category);

            if(!categoryExpense.isEmpty()){
                hasAny = true;
                double total = getTotalByCategory(category);

                System.out.printf("%s: %d expenses | %.2f PLN%n",
                        category.getLabel(),
                        categoryExpense.size(),
                        total);
            }
        }

        if(!hasAny){
            System.out.println("No expenses in any category yet.");
        }

        System.out.println("───────────────────────────────────────");
    }

    /**
     * Finds expenses by category and displays them.
     * Convenience method combining find + display
     *
     * @param category category to search
     * @return count of found expenses
     */
    public int findAndDisplayByCategory(Category category){
        List<Expense> found = findByCategory(category);

        if(found.isEmpty()){
            System.out.println("No expenses in category: " + category.getDisplayName());
            return  0;
        }

        System.out.println("\n" + category.getLabel() + " Expenses:");
        System.out.println("───────────────────────────────────────");
        for (int i = 0; i < found.size(); i++) {
            System.out.print((i + 1) + ". ");
            found.get(i).displayInfo();
        }
        System.out.println("───────────────────────────────────────");
        System.out.println("Total: " + String.format("%.2f PLN", getTotalByCategory(category)));

        return found.size();
    }


    /**
     * Add common preset expenses for testing.
     * Uses varargs internally.
     */
    public void addPresetExpenses(){
        Expense[] presets = {
                new Expense("Morning Coffee", 15, Category.FOOD, "2025-01-20"),
                new Expense("Lunch", 45, Category.FOOD, "2025-01-20"),
                new Expense("Bus Ticket", 4.5, Category.TRANSPORT, "2025-01-20"),
                new Expense("Movie", 30, Category.ENTERTAINMENT, "2025-01-20"),
                new Expense("Rent", 1500, Category.HOUSING, "2025-01-01")
        };

        addExpense(presets);
    }
}
