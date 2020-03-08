package customExceptions;

@SuppressWarnings("serial")
public class MissingInformationException extends Exception {
	private String name;
	private String surname;
	private String documentType;
	private String documentNumber;
	private String customMessage;
	
	/**
	 * Constructs a MissingInformationException when trying to create a user.
	 * @param n Name, null if was missing.
	 * @param s Surname, null if was missing.
	 * @param dT Document type, null if was missing.
	 * @param dN Document number, null if was missing.
	 */
	public MissingInformationException(String n, String s, String dT, String dN) {
		super("At least one of the following is missing:\n1.Name\n2.Surname\n3.Document Type\n4.DocumentNumber");
		name = n;
		surname = s;
		documentType = dT;
		documentNumber = dN;
		setCustomMessage();
	}
	/**
	 * Sets custom message detailing the missing field or fields.
	 */
	private void setCustomMessage() {
		customMessage += "The following fields are missing:\n";
		int i = 1;
		if(name.equals("")) {
			customMessage += i + ". Name";
			i++;
		}
		if(surname.equals("")) {
			customMessage += i + ". Surname";
			i++;		
		}
		if(documentType.equals("")) {
			customMessage += i + ". Document Type";
			i++;
		}
		if(documentNumber.equals("")) {
			customMessage += i + ". Document Number";
			i++;
		}
	}	
	/**
	 * Returns the customMessage with the missing data field or fields.
	 * @return customMessage Message with the missing data field or fields
	 */
	public String getCustomMessage() {
		return customMessage;
	}

	

}
