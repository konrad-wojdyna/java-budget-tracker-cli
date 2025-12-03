package exception;


/**
 * Thrown when expense data is invalid (validation failure).
 *
 * This is an UNCHECKED exception (extends RuntimeException).
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */
public class InvalidExpenseDataException extends RuntimeException {

    private String fieldName;
    private Object invalidValue;

    /**
     * Creates exception with message.
     */
    public InvalidExpenseDataException(String message) {
        super(message);
    }

    public InvalidExpenseDataException(String message, String fieldName, Object invalidValue){
        super(message + " Field: " + fieldName + ", Value: " + invalidValue);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }
}
