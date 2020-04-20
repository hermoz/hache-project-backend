package hmm.architecturestudio.management.exception;

/**
 * Represents exception on validation services
 * @author Herminia
 *
 */
public class ValidationServiceException extends Exception {

	public ValidationServiceException(String message) {
        super("Validation error in service: " + message);
    }
	
}
