package model;

public class User {	
	public final static String CEDULA_DE_CIUDADANIA = "CC";
	public final static String TARJETA_DE_IDENTIDAD = "TI";
	public final static String CEDULA_DE_EXTRANJERIA = "CE";
	public final static String PASSPORT = "P";
	public final static String REGISTRO_CIVIL = "RC";
	private String name;
	private String surname;
	private String documentType;
	private String documentNumber;
	private String phoneNumber;
	private String address;
	private Turn assignedTurn;
	/**
	 * Creates a new user given its name, surname, document type, document number, phone number and address.
	 * @param n Name.
	 * @param s Surname.
	 * @param dT Document type.
	 * @param dN Document number.
	 * @param p Phone number.
	 * @param a Address.
	 */
	public User(String n, String s, String dT, String dN, String p,	String a){
		name = n;
		surname = s;
		documentType = dT;
		documentNumber = dN;
		phoneNumber = p;
		address = a;
		assignedTurn = null;
	}
	/**
	 * Returns the user name.
	 * <b>PRE:</b> A user must be created.
	 * @return name User name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the user surname.
	 * <b>PRE:</b> A user must be created.
	 * @return surname User surname.
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * Returns the user document type.
	 * <b>PRE:</b> A user must be created.
	 * @return documentType User document type.
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * Returns the user document number.
	 * <b>PRE:</b> A user must be created.
	 * @return documentNumber User document number.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	/**
	 * Returns the user phone number.
	 * <b>PRE:</b> A user must be created
	 * @return phoneNumber User phone number.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * Returns the user address.
	 * <b>PRE:</b> A user must be created.
	 * @return address User address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Returns assigned turn as a String, returns null if there is no assigned turn.
	 * @return assignedTurn Assigned turn as a String, null if no assigned turn.
	 */
	public String getAssignedTurnStr() {
		return assignedTurn.getTurn();
	}
	/**
	 * Assigns a turn to the user.
	 * @param turn Turn to assign
	 */
	public void setAssignedTurn(Turn turn) {
		assignedTurn = turn;
	}
	/**
	 * Returns assigned turn.
	 * @return assignedTurn
	 */
	public Turn getAssignedTurn() {
		return assignedTurn;
	}	
}