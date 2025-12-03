package exception;

public class AmountTooLargeException extends RuntimeException {

    private double amount;
    private double maxAllowed;

    public AmountTooLargeException(String message) {
        super(message);
    }

    public AmountTooLargeException(String message, double amount, double maxAllowed){
        super("Amount "  + amount + " exceeds maximum " + maxAllowed);
        this.amount = amount;
        this.maxAllowed = maxAllowed;
    }


    @Override
    public String getMessage() {
        return super.getMessage() + ": Amount " + amount + " exceeds maximum " + maxAllowed;
    }
}
