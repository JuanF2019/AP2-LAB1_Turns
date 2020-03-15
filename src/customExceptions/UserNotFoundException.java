package customExceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
	private String documentNumber;
	
	/**
	 * Constructs a UserNotFoundException when trying to find a user.
	 * @param dT Document type, user document type.
	 * @param dN Document number, user document number.
	 */
	public UserNotFoundException(String dN) {
		super("User with document " + dN + " not found, please register." );	
		this.documentNumber = dN;
	}	
	/**
	 * Returns document number that caused the exception to be thrown
	 * @return dN Document Number
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
}
