package model;

/**
 * Represents an icome transaction.
 *
 * Extends Transaction with icome-specific field (source).
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */
public class Income extends Transaction{

    private String source;

    /**
     * Constructor for all transactions.
     *
     * @param date        transaction date (YYYY-MM-DD)
     * @param amount      transaction amount (must be positive)
     * @param description transaction description
     * @throws IllegalArgumentException if amount is negative or date is empty
     */
    public Income(String date, double amount, String description, String source) {
        super(date, amount, description);

        if(source == null || source.trim().isEmpty()){
            throw new IllegalArgumentException("Source cannot be empty");
        }

        this.source = source;
    }

    /**
     * Constructor without description
     * @param date income date
     * @param amount icome amount
     * @param source income source
     */
    public Income(String date, double amount, String source){
        this(date, amount, "", source);
    }

    public String getSource() {
        return source;
    }

    @Override
    public String getType() {
        return "INCOME";
    }
    @Override
    public void displayInfo() {
        System.out.printf("[%s] %s | +%.2f PLN | Source: %s%n",
                getType(),
                getDate(),
                getAmount(),
                source);

        if (!getDescription().isEmpty()) {
            System.out.println("  ↳ " + getDescription());
        }
    }

}
