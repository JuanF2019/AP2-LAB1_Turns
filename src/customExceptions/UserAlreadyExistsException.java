package customExceptions;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends Exception {
	
	private String documentNumber;	
	/**
	 * Constructs a UserAlreadyExistException given the document type and number that caused the exception to be thrown.
	 * @param dT Document type.
	 * @param dN Document number.
	 */
	public UserAlreadyExistsException(String dN) {
		super("User with document " + dN + " already exists.");
		documentNumber = dN;					
	}	
	
	/**
	 * Returns document number that caused the exception to be thrown.
	 * @return documentNumber Document number.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	
}
