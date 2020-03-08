package customExceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
	private String documentType;
	private String documentNumber;
	
	/**
	 * Constructs a UserNotFoundException when trying to find a user.
	 * @param dT Document type, user document type.
	 * @param dN Document number, user document number.
	 */
	public UserNotFoundException(String dT, String dN) {
		super("User with document "  + dT + " " + dN + " not found, please register." );	
		this.documentType = dT;
		this.documentNumber = dN;
	}
	/**
	 * Returns document type that caused the exception to be thrown.
	 * @return dT Document type
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * Returns document number that caused the exception to be thrown
	 * @return dN Document Number
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
}
