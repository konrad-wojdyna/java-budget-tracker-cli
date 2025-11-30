package model;

/**
 * Represents a single expense entry in the budget tracker.
 *
 * This class encapsulates all expense-related data with proper
 * validation and follows OOP principles.
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */

public class Expense {
    private String name;
    private double amount;
    private Category category;
    private String date;
    private String description;
    private Priority priority;

    /**
     * Creates a new expense with full details including priority.
     *
     * @param name expense name
     * @param amount expense amount (must be positive)
     * @param category expense category (enum)
     * @param date expense date in YYYY-MM-DD format
     * @param description optional description
     * @param priority expense priority level
     * @throws IllegalArgumentException if validation fails
     */
    public Expense(String name, double amount, Category category, String date, String description,
                   Priority priority){
        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Category cannot be null");
        }

        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        if(priority == null){
            throw new IllegalArgumentException("Priority cannot be null");
        }

        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Creates expense without priority (defaults to MEDIUM)
     *
     * @param name the expense name
     * @param amount the expense amount (must be positive)
     * @param category the expense category
     * @param date the expense date in YYYY-MM-DD format
     * @param description the expense description
     */
    public Expense(String name, double amount, Category category, String date, String description){
        this(name, amount, category, date, description, Priority.MEDIUM);
      }

    /**
     * Creates a new expense without description or priority.
     *
     * @param name expense name
     * @param amount expense amount
     * @param category expense category
     * @param date expense date
     */

    public Expense(String name, double amount, Category category, String date){
        this(name, amount, category, date, "", Priority.MEDIUM);
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * Update expense amount with validation
     *
     * @param amount new amount in PLN, must be positive
     * @throws IllegalArgumentException if amount is negative
     */

    public void setAmount(double amount) {
        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }
        this.amount = amount;
    }

    /**
     * Displays formatted expense information to console
     * Shows description only if present
     */

    public void displayInfo(){
        if(description != null && !description.isEmpty()){
        System.out.printf("%s: %s - %.2f PLN (Date: %s) Description: %s%n",
                category, name, amount, date, description);
        }else {
            System.out.printf("%s: %s - %.2f PLN (Date: %s) [%s]%n",
                    category.getLabel(), name, amount, date, priority);
        }
    }

    /**
     * Check if expense exceeds 500 PLN
     * @return
     */
    public boolean isExpensive() {
        return amount > 500;
    }

    /**
     * Checks if expense is from specified month.
     *
     * @param month in YYYY-MM format
     * @return true if date starts with month
     */
    public boolean isFromMonth(String month){
        return this.date.startsWith(month);
    }
}
