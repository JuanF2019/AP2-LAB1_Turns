package customExceptions;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends Exception {
	private String documentType;
	private String documentNumber;	
	/**
	 * Constructs a UserAlreadyExistException given the document type and number that caused the exception to be thrown.
	 * @param dT Document type.
	 * @param dN Document number.
	 */
	public UserAlreadyExistsException(String dT, String dN) {
		super("User with document " + dT + " " + dN + " already exists.");
		documentType = dT;
		documentNumber = dN;					
	}	
	/**
	 * Returns document type that caused the exception to be thrown.
	 * @return documentType Document type
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * Returns document number that caused the exception to be thrown.
	 * @return documentNumber Document number.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	
}
