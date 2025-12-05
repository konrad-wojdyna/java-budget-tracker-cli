package service;

import exception.ExpenseNotFoundException;
import exception.InvalidExpenseDataException;
import model.Category;
import model.Expense;
import model.Priority;
import repository.ExpenseRepository;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

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
 * @version 0.7.0
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
        System.out.println("✓ Added: " + expense.getDescription());
    }

    /**
     * Creates and adds expense with all details.
     * Convenience method to avoid creating Expense object separately.
     *
     * @param amount expense amount
     * @param category expense category
     * @param date expense date
     * @param description expense description
     * @param priority expense priority
     */
    public void addExpense(String date, double amount, String description, Category category, Priority priority){
        Expense expense = new Expense(date, amount, description, category, priority);
        addExpense(expense);
    }

    /**
     * Creates and adds expense without priority (defaults to MEDIUM).
     *
     * @param amount expense amount
     * @param category expense category
     * @param date expense date
     * @param description expense description
     */
    public void addExpense(String date, double amount, String description, Category category){
        addExpense(date, amount, description, category, Priority.MEDIUM);
    }

    /**
     * Creates and adds expense without description or priority.
     *
     * @param amount expense amount
     * @param category expense category
     */
    public void addExpense(String date, double amount, Category category){
        addExpense(date, amount, "", category, Priority.MEDIUM);
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
        try{
        Expense expense = repository.findById(index);
        repository.delete(index);
        System.out.println("Removed: " + expense.getDescription());

        }catch (ExpenseNotFoundException e){
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("Failed to remove expense", e);
        }catch (InvalidExpenseDataException e){
            System.out.println("Invalid data: " + e.getMessage());
            throw e;
        }
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
                new Expense("2025-01-20", 15, "Morning Coffee", Category.FOOD),
                new Expense("2025-01-20", 45, "Lunch at restaurant", Category.FOOD),
                new Expense("2025-01-20", 4.5, "Bus Ticket", Category.TRANSPORT),
                new Expense("2025-01-20", 30, "Movie night", Category.ENTERTAINMENT),
                new Expense("2025-01-01", 1500, "Monthly rent", Category.HOUSING)
        };

        addExpense(presets);
    }

    /**
     * Calculates total spending for each category using HashMap.
     * More efficient than multiple loops - 0(n) instead of 0(n*m).
     *
     * @return map of category to total amount
     */
    public Map<Category, Double> calculateTotalsByCategory(){
        Map<Category, Double> totals = new HashMap<>();

        //Initialize all categories with 0.0
        for(Category category : Category.values()){
            totals.put(category, 0.0);
        }

        //Sum expenses for each category (single loop)
        List<Expense> allExpenses = repository.findAll();
        for(Expense expense : allExpenses){
            Category cat = expense.getCategory();
            double currentTotal = totals.get(cat);
            totals.put(cat, currentTotal + expense.getAmount());
        }

        return totals;
    }

    /**
     * Gets count of expenses per category.
     *
     * @return map of category to expense count
     */
    public Map<Category, Integer> getExpenseCountByCategory(){
        Map<Category, Integer> counts = new HashMap<>();

        //Initialize with 0
        for(Category category : Category.values()){
            counts.put(category, 0);
        }

        //Count expenses
        List<Expense> expenses = repository.findAll();
        for(Expense expense : expenses){
            Category cat = expense.getCategory();
            int currentCount = counts.get(cat);
            counts.put(cat, currentCount + 1);
        }

        return counts;
    }

    /**
     * Gets count of expenses per priority level.
     *
     * @return map of priority to expense count
     */
    public Map<Priority, Integer> getExpenseCountByPriority(){
        Map<Priority, Integer> counts = new HashMap<>();

        //Initialize
        for(Priority priority : Priority.values()){
            counts.put(priority, 0);
        }

        //Count
        List<Expense> allExpenses = repository.findAll();
        for(Expense expense : allExpenses){
            Priority p = expense.getPriority();
            counts.put(p, counts.get(p) + 1);
        }

        return  counts;
    }

    /**
     * Get all unique dates that have expenses.
     *
     * @return set of unique dates
     */
    public Set<String> getUniqueDates(){
        Set<String> dates = new HashSet<>();

        List<Expense> allExpenses = repository.findAll();
        for(Expense expense : allExpenses){
            dates.add(expense.getDate());
        }

        return  dates;
    }

    /**
     * Get total spending per date.
     *
     * @return map of date to total amount
     */
    public Map<String, Double> getTotalsByDate(){
       Map<String, Double> totals = new HashMap<>();

       List<Expense> allExpenses = repository.findAll();
       for(Expense expense : allExpenses){
           String date = expense.getDate();
           double currentTotal = totals.getOrDefault(date, 0.0);
           totals.put(date, currentTotal + expense.getAmount());
       }

       return totals;
    }

    public void displayAdvancedStatistics(){
        System.out.println("Advanced Budget Statistics");

        List<Expense> allExpenses = repository.findAll();

        if(allExpenses.isEmpty()){
            System.out.println("No expenses to analyze.");
            return;
        }

        //Overall totals
        double grandTotal = calculateTotal();
        System.out.println("\n📊 Overall Statistics:");
        System.out.println("───────────────────────────────────────");
        System.out.printf("Total Expenses: %d%n", allExpenses.size());
        System.out.printf("Total Amount: %.2f PLN%n", grandTotal);
        System.out.printf("Average Expense: %.2f PLN%n", grandTotal / allExpenses.size());

        //Category breakdown
        System.out.println("\n📂 By Category:");
        System.out.println("───────────────────────────────────────");

        Map<Category, Double> categoryTotals = calculateTotalsByCategory();
        Map<Category, Integer> categoryCounts = getExpenseCountByCategory();

        for(Category category : Category.values()){
            int count = categoryCounts.get(category);
            double total = categoryTotals.get(category);

            if(count > 0){
                double percentage = (total / grandTotal) * 100;
                double average = total / count;

                System.out.printf("%s%n", category.getLabel());
                System.out.printf("  Count: %d | Total: %.2f PLN (%.1f%%)%n",
                        count, total, percentage);
                System.out.printf("  Average: %.2f PLN%n", average);
            }
        }

        // Priority breakdown
        System.out.println("\n⚡ By Priority:");
        System.out.println("───────────────────────────────────────");

        Map<Priority, Integer> priorityCounts = getExpenseCountByPriority();

        for (Priority priority : Priority.values()) {
            int count = priorityCounts.get(priority);
            if (count > 0) {
                double percentage = (count * 100.0) / allExpenses.size();
                System.out.printf("%s: %d expenses (%.1f%%)%n",
                        priority, count, percentage);
            }
        }

        // Date analysis
        System.out.println("\n📅 Date Analysis:");
        System.out.println("───────────────────────────────────────");

        Set<String> uniqueDates = getUniqueDates();
        System.out.printf("Unique dates with expenses: %d%n", uniqueDates.size());

        Map<String, Double> dateTotals = getTotalsByDate();

        // Find most expensive date
        String maxDate = null;
        double maxAmount = 0;

        for (Map.Entry<String, Double> entry : dateTotals.entrySet()) {
            if (entry.getValue() > maxAmount) {
                maxAmount = entry.getValue();
                maxDate = entry.getKey();
            }
        }

        if (maxDate != null) {
            System.out.printf("Most expensive date: %s (%.2f PLN)%n", maxDate, maxAmount);
        }

        System.out.println("───────────────────────────────────────");
    }

    /**
     * Get expenses by month
     *
     * @return map expenses by month
     */
    public Map<String, List<Expense>> getExpensesByMonth(String month){
        Map<String, List<Expense>> expensesByMonth = new HashMap<>();

        List<Expense> allExpenses = repository.findAll();

        for(Expense expense : allExpenses){
            String date = expense.getDate();

            if(date.startsWith(month)){
                List<Expense> expensesOnDate = expensesByMonth.getOrDefault(date, new ArrayList<>());
                expensesOnDate.add(expense);

                expensesByMonth.put(date, expensesOnDate);
            }
        }

            System.out.println("Expenses in " + month + " grouped by date: ");
        for(Map.Entry<String, List<Expense>> entry : expensesByMonth.entrySet()){
            System.out.println(entry.getKey());
            for(int i=0; i<entry.getValue().size(); i++){
                System.out.print((i+1) + ". ");
                entry.getValue().get(i).displayInfo();
            }
        }

        return expensesByMonth;
    }

    /**
     * Finds category with most expenses (by count).
     *
     * @return most popular category, or null if no expenses
     */
    public Category findMostPopularCategory(){
        Map<Category, Integer> counts = getExpenseCountByCategory();

        Category mostPopular =null;
        int maxCount = 0;

        for(Map.Entry<Category, Integer> entry : counts.entrySet()){
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
                mostPopular = entry.getKey();
            }
        }

        return  mostPopular;
    }

    public Expense getExpenseByIndex(int index) throws ExpenseNotFoundException{
        if(index < 0){
            throw new InvalidExpenseDataException("Index cannot be negative", "index", index);
        }

        try{
            return  repository.findById(index);
        }catch (ExpenseNotFoundException e){
            System.out.println("Expense not found: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Saves all expenses to CSV file.
     *
     * @param fileName path to save file
     * @throws IOException if file write fails
     */
    public void saveToFile(String fileName) throws IOException {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){

            writer.write("date,amount,description,category,priority");
            writer.newLine();

            List<Expense> allExpenses = repository.findAll();
            for(Expense expense : allExpenses){

                String desc = expense.getDescription();

                if(desc.contains(",") || desc.contains("\"")){
                    desc = "\"" + desc.replace("\"", "\"\"") + "\"";
                }

                String line = String.format("%s,%s,%s,%s,%s",
                        expense.getDate(),
                        expense.getAmount(),
                        desc,
                        expense.getCategory().name(),  // FOOD, TRANSPORT, etc.
                        expense.getPriority().name()); // LOW, MEDIUM, HIGH

                writer.write(line);
                writer.newLine();
            }

            System.out.println("✓ Saved " + allExpenses.size() +
                    " expenses to " + fileName);
        }
    }


    /**
     * Loads expenses from CSV file.
     *
     * @param filename path to load file
     * @throws IOException if file read fails
     */
    public void loadFromFile(String filename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("⚠️  File not found: " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            // Skip header line
            String header = reader.readLine();

            int loadedCount = 0;
            int errorCount = 0;
            String line;

            while ((line = reader.readLine()) != null) {
                try {

                    String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

                    // Parse CSV line
                    String[] parts = line.split(regex);


                    if (parts.length != 5) {
                        System.out.println("⚠️  Skipping invalid line: " + line);
                        errorCount++;
                        continue;
                    }
                    String date = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    if(parts[2].startsWith("\"")){
                        parts[2] = parts[2].substring(1, parts[2].length() - 1);
                    }
                    String description = parts[2];
                    Category category = Category.valueOf(parts[3]);
                    Priority priority = Priority.valueOf(parts[4]);

                    // Create and add expense
                    Expense expense = new Expense(date, amount, description,
                            category, priority);
                    repository.save(expense);
                    loadedCount++;


                } catch (NumberFormatException e) {
                    System.out.println("⚠️  Invalid number in line: " + line);
                    errorCount++;
                } catch (IllegalArgumentException e) {
                    System.out.println("⚠️  Invalid category/priority in line: " + line);
                    errorCount++;
                } catch (Exception e) {
                    System.out.println("⚠️  Error parsing line: " + line);
                    errorCount++;
                }
            }

            System.out.println("✓ Loaded " + loadedCount + " expenses from " + filename);
            if (errorCount > 0) {
                System.out.println("⚠️  " + errorCount + " lines had errors");
            }
        }
    }

}
