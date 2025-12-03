package exception;

/**
 * Thrown when an expense already exists/
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */

public class DuplicateExpenseException extends RuntimeException {

    private String date;
    private String description;

    public DuplicateExpenseException(String message) {
        super(message);
    }

    public DuplicateExpenseException(String message, String date, String description){
        super(message + " " + "Duplicate expense detected on " + date + ":" + description);
        this.date = date;
        this.description = description;
    }
}
