package model;


/**
 * Abstract base class for all financial transactions.
 *
 * Provides common fields and methods for Expense, Income and Budget.
 * Cannot be instantiated directyl - must use conrete subclasses.
 *
 * This demonstrates INHERITANCE AND POLYMORPHISM concepts.
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */
public abstract class Transaction {

    private final String date;
    private final double amount;
    private final String description;


    /**
     * Constructor for all transactions.
     *
     * @param date transaction date (YYYY-MM-DD)
     * @param amount transaction amount (must be positive)
     * @param description transaction description
     * @throws IllegalArgumentException if amount is negative or date is empty
     */
    public Transaction(String date, double amount, String description){
        if(date == null || date.trim().isEmpty()){
            throw new IllegalArgumentException("Date cannot be empty");
        }

        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.date =date;
        this.amount = amount;
        this.description = description != null ? description : "";
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Abstract method - each subclass must define its type.
     *
     * @return the transaction type (e.g., "EXPENSE", "INCOME")
     */
    public abstract String getType();

    /**
     * Display transaction information.
     * Can be overridden by subclasses for custom formatting.
     */
    public void displayInfo() {
        System.out.printf("[%s] %s | %.2f PLN | %s%n",
                getType(), date, amount, description);
    }

    /**
     * Checks if this transaction is from specified month.
     *
     * @param month to check (YYYY-MM format)
     * @return true if transaction is from that month
     */
    public boolean isFromMonth(String month){
        return date.startsWith(month);
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f PLN on %s", getType(), amount, date);
    }
}
