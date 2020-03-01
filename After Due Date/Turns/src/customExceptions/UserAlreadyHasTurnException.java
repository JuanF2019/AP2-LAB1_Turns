package customExceptions;

@SuppressWarnings("serial")
public class UserAlreadyHasTurnException extends Exception {
	
	private String documentType;
	private String documentNumber;
	/**
	 * Constructs a UserAlreadyHasTurnException given document type, number and turn.
	 * @param dT Document type
	 * @param dN Document number
	 * @param turnStr String representing a turn
	 */
	public UserAlreadyHasTurnException(String dT, String dN,String turnStr) {
		super("User with document "  + dT + " " + dN + " already has a turn: " + turnStr);
		this.documentType = dT;
		this.documentNumber = dN;
	}
	/**
	 * Returns document type that caused the exception to be thrown.
	 * @return documentType
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * Returns document number that caused the exception to be thrown.
	 * @return documentNumber
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	
	

	
}
