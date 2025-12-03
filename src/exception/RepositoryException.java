package exception;


/**
 * Thrown when repository operations fail.
 *
 * Wraps underlying storage errors.
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */
public class RepositoryException extends RuntimeException {

    /**
     * Creates exception with message.
     * @param message
     */
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause){
        super(message, cause);
    }

}
