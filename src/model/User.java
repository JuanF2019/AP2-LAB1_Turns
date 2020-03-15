package model;
import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{	
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
	private boolean[] attended;
	private Restriction restriction;
	private String pastTurns;
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
		restriction = null;
		attended = new boolean[2];
		pastTurns = "";
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
	public String toStringTurn() {
		if(assignedTurn!= null) {
			return assignedTurn.toString();
		}
		else {
			return "No assigned turn!";
		}
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
	
	public double getRestrictionDuration() {
		if(restriction != null) {
			return restriction.getDuration();
		}
		else {
			return 0;
		}
	}
	public boolean[] getAttended() {
		return attended;
	}	
	
	public void setRestriction(double days) {
		restriction = new Restriction(days);
	}
	
	public void setRestriction() {
		restriction = null;
		attended[0] = true;
		attended[1] = true;
	}
	
	public void addPastTurn(String turn) {
		pastTurns += turn + "\n";
	}
	
	public String getPastTurns() {
		return pastTurns;
	}
}