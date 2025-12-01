package model;

/**
 * Represents an expense transaction.
 *
 * Entends Transaction with expense-specific fields (category, priority).
 * Demonstates INHERITANCE - Expense IS-A Transaction.
 *
 * @author Konrad Wojdyna
 * @version 0.2.0
 */

public class Expense extends Transaction {

    private final Category category;
    private final Priority priority;

    /**
     * Creates a new expense with full details including priority.
     *
     * @param amount expense amount (must be positive)
     * @param category expense category (enum)
     * @param date expense date in YYYY-MM-DD format
     * @param description optional description
     * @param priority expense priority level
     * @throws IllegalArgumentException if validation fails
     */
    public Expense(String date, double amount, String description, Category category, Priority priority){
        super(date, amount, description);

        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        if(priority == null){
            throw new IllegalArgumentException("Priority cannot be null");
        }

        this.category = category;
        this.priority = priority;
    }

    /**
     * Creates expense without priority (defaults to MEDIUM)
     *
     * @param amount the expense amount (must be positive)
     * @param category the expense category
     * @param date the expense date in YYYY-MM-DD format
     * @param description the expense description
     *
     */
    public Expense(String date, double amount, String description, Category category){
        this(date, amount, description, category, Priority.MEDIUM);
      }

    /**
     * Creates a new expense without description or priority.
     *
     * @param amount expense amount
     * @param category expense category
     * @param date expense date
     */

    public Expense(String date, double amount, Category category){
        this(date, amount, "", category,  Priority.MEDIUM);
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String getType() {
        return "EXPENSE";
    }

    public Priority getPriority() {
        return priority;
    }

//    /**
//     * Update expense amount with validation
//     *
//     * @param amount new amount in PLN, must be positive
//     * @throws IllegalArgumentException if amount is negative
//     */
//
//    public void setAmount(double amount) {
//        if(amount < 0){
//            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
//        }
//        this.amount = amount;
//    }

    /**
     * Displays formatted expense information to console
     * Shows description only if present
     */

    @Override
    public void displayInfo(){
        System.out.printf("[%s] %s | %.2f PLN | %s | [%s]%n",
                getType(),
                getDate(),  // inherited from Transaction!
                getAmount(),  // inherited!
                category.getLabel(),
                priority);

        if (!getDescription().isEmpty()) {
            System.out.println("  ↳ " + getDescription());
        }
    }

    /**
     * Check if expense is expensive over 500 PLN
     *
     * @return true or false
     */
    public boolean isExpensive() {
        return getAmount() > 500;
    }
}
