package model;

public class Budget extends Transaction{

    private String period;
    private Category category;

    /**
     * Constructor for all transactions.
     *
     * @param date        transaction date (YYYY-MM-DD)
     * @param amount      transaction amount (must be positive)
     * @param description transaction description
     * @throws IllegalArgumentException if amount is negative or date is empty
     */
    public Budget(String date, double amount, String description, String period, Category category) {
        super(date, amount, description);

        if(period == null || period.trim().isEmpty()){
            throw new IllegalArgumentException("Period cannot be empty");
        }

        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        this.period = period;
        this.category = category;
    }

    public String getPeriod() {
        return period;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String getType() {
        return "BUDGET";
    }

    @Override
    public void displayInfo() {
        System.out.printf("[%s] %s | %.2f PLN | Period: %s | %s%n",
                getType(),
                getDate(),
                getAmount(),
                period,
                category.getLabel());

        if (!getDescription().isEmpty()) {
            System.out.println("  ↳ " + getDescription());
        }
    }
}
