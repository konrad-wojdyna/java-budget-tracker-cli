package exception;

/**
 * Thrown when an expense cannot be found by ID or criteria.
 *
 * This is a CHECKED exception, forcing callers to handle it.
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */

public class ExpenseNotFoundException extends RuntimeException {

    private int requestedIndex;

    /**
     * Creates exception with message.
     * @param message to display
     */
    public ExpenseNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates exception with message and requested index.
     */
    public ExpenseNotFoundException(String message, int requestedIndex){
        super(message);
        this.requestedIndex = requestedIndex;
    }

    /**
     * Creates exception with message and cause.
     */
    public ExpenseNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public int getRequestedIndex(){
        return requestedIndex;
    }

}
