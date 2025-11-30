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
    private String category;
    private String date;

    /**
     * Constructs a new Expense with specified details.
     *
     * @param name the expense name/description
     * @param amount the expense amount (must be positive)
     * @param category the expense category
     * @param date the expense date in YYYY-MM-DD format
     * @throws IllegalArgumentException if amount is negative
     */
    public Expense(String name, double amount, String category, String date){
        if(amount < 0){
            throw new IllegalArgumentException("Amount canot be negative: " + amount);
        }

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public void setAmount(double amount) {
        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }
        this.amount = amount;
    }

    public void displayInfo(){
        System.out.printf("%s: %s - %.2f PLN (Date: %s)%n",
                category, name, amount, date);
    }

    public boolean isExpensive() {
        return amount > 500;
    }
}
