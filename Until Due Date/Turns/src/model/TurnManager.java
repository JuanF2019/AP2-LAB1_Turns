package model;
import customExceptions.*;
import java.util.ArrayList;

public class TurnManager {
	private Turn currentTurn;	
	private ArrayList<User> users;
	private ArrayList<Turn> turns;
	/**
	 * Constructs an empty users list, sets current turn to null and fills turns list with available turn.<br>
	 * <b>POS:</b><br>1. Users list initialized.<br>
	 * 2. Current turn set to null.<br>
	 * 3. Turn list filled with available turns. 
	 */
	public TurnManager() {
		users = new ArrayList<User>();
		turns = new ArrayList<Turn>();
		for(int j = (int)Turn.MIN_LETTER; j < (int)Turn.MAX_LETTER;j++ ) {
			for (int k = Turn.MIN_NUMBER; k <= Turn.MAX_NUMBER; k++) {
				turns.add(new Turn((char)j,k,true));
			}
		}					
		currentTurn = null;
	}
	
	/**
	 * Adds a new user given its name, surname, document type, document number, phone number and address, if the user does not already exists.
	 * @param n Name.
	 * @param s Surname.
	 * @param dT Document type.
	 * @param dN Document number.
	 * @param p Phone number.
	 * @param a Address.
	 * @throws UserAlreadyExistsException
	 * 
	 */
	public void addUser(String n, String s, String dT, String dN, String p,	String a) throws UserAlreadyExistsException, MissingInformationException{
		try {	
			findUserByDTDN(dT,dN);//Throws UserNotFoundException,if is thrown then user does not exist and can be added. 
			throw new UserAlreadyExistsException(dT,dN);			
		}
		catch(UserNotFoundException ex){
			if(n.equals("") || s.equals("") || dT.equals("") || dN.equals("") ) {
				throw new MissingInformationException(n,s,dT,dN);
			}
			users.add(new User(n,s,dT,dN,p,a));
		}
	}
	/**
	 * Returns the position of a user in the users list given its document number. Returns -1 if no user was found.
	 * @param dT Document type
	 * @param dN Document number
	 * @return position User position.
	 */
	public int findUserByDTDN(String dT, String dN) throws UserNotFoundException{
		int pos = -1;
		User tempUser = null;
		boolean check = false;
		for(int i = 0; i < users.size() && !check; i++) {
			tempUser = users.get(i);
			if (tempUser.getDocumentNumber().equals(dN)) {
				check = true;
				pos = i;
			}
		}
		if (pos == -1) {
			throw new UserNotFoundException(dT,dN);
			
		}
		return pos;
	}
	/**
	 * Finds a user given a turn, and returns is position, -1 if no user was found.
	 * @param turn Turn the user is supposed to have.
	 * @return position Position of the user in the users list.
	 */
	public int findUserByTurn(Turn turn){
		int pos = -1;
		User tempUser = null;
		boolean check = false;
		for(int i = 0; i < users.size() && !check; i++) {
			tempUser = users.get(i);
			if (tempUser.getAssignedTurn().isEqualTurn(turn)) {
				check = true;
				pos = i;
			}
		}
		return pos;
	}
	/**
	 * Finds next turn given its availability, and returns its position, returns -1 if no turn was found.
	 * @param a Availability, turn availability.
	 * @return position Position, turn position, -1 if no turn was not found.
	 */
	public int findNextTurn(boolean a) {
		int pos = -1;
		Turn tempTurn = null;
		boolean check = false;
		if(currentTurn == null) {
			pos = 0;
		}
		else if(getTurnPos(currentTurn) != turns.size()-1) {
			for(int i = getTurnPos(currentTurn)+1; i < turns.size() && !check; i++) {
				tempTurn = turns.get(i);
				if (tempTurn.isAvailable() == a) {
					check = true;
					pos = i;
				}
			}
		}		
		else {
			for(int i = 0; i < turns.size() && !check; i++) {
				tempTurn = turns.get(i);
				if (tempTurn.isAvailable() == a) {
					check = true;
					pos = i;
				}
			}
		}
		return pos;
	}
	/**
	 * Returns the list of the registered users.<br>
	 * <b>PRE: </b>The users list must be initialized. <br>
	 * @return users - The list of the registered users.
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	/**
	 * Returns turn position in the turns list given the turn.
	 * @param turn Turn to get its position.
	 * @return position Position in the turns list
	 */
	private int getTurnPos(Turn turn) {
		int pos = -1;
		boolean check = false;
		
		for(int i = 0; i < turns.size() && !check; i++) {
			if (turn.isEqualTurn(turns.get(i))) {
				check = true;
				pos = i;
			}
		}
		
		return pos;
	}
	/**
	 * Assigns a turn to a existing user.
	 * @param dT Document type
	 * @param dN Document number
	 * @return message Message with turn represented as a String.
	 * @throws UserNotFoundException If the user with the given document type and number was not found.
	 */
	public String assignTurn(String dT, String dN) throws UserNotFoundException{
		String msg = "";
		int i = findUserByDTDN(dT,dN);
		int j = -1;
		if (i != -1) {
			User user = users.get(i);
			if (user.getAssignedTurn() == null) {
				j = findNextTurn(true);
				Turn turn = turns.get(j);
				if(j !=-1) {
					turn.setAvailable(false);
					user.setAssignedTurn(turn);
					msg = user.getAssignedTurnStr();
					users.add(i,user); //Updates users lists.
					turns.add(j,turn); //Updates turns lists.
				}	
				
			}			
		}	
		else {
			throw new UserNotFoundException(dT,dN);
		}
		return msg;
	}
	/**
	 * Advances turn to the next one if the next turn is assigned.<br>
	 * <b>POS:</b><br>1. Updates currentTurn with next turn. <br>2. Updates turns and user lists.
	 * @throws NoAssignedTurnsException If there are no assigned turns.
	 */
	public void advanceTurn() throws NoAssignedTurnsException{
		User tempUser = null;
		Turn tempTurn = null;
		int userPos = -1;
		int turnPos = -1;
		
		if(currentTurn != null) {
			turnPos = getTurnPos(currentTurn);
			tempTurn = turns.get(turnPos);
			tempTurn.setAvailable(true);
			turns.set(turnPos, tempTurn);
		}
			
		
		if (currentTurn == null || currentTurn.isEqualTurn(turns.get(turns.size()-1)) ) {
			tempTurn = turns.get(0);
			userPos = findUserByTurn(tempTurn);
			if (userPos != -1) {				
				currentTurn = tempTurn;
				tempUser = users.get(userPos);
				tempUser.setAssignedTurn(null);				
				turns.set(0, tempTurn);
				users.set(userPos, tempUser);				
			}
			else {
				System.out.println(1);
				throw new NoAssignedTurnsException(); 
			}
		}
		else{
			if (findNextTurn(false) != -1) {
				turnPos = findNextTurn(false);
				tempTurn = turns.get(turnPos);				
				userPos = findUserByTurn(tempTurn);
				if (userPos != -1) {
					currentTurn = tempTurn;
					tempUser = users.get(userPos);
					tempUser.setAssignedTurn(null);					
					turns.set(turnPos, tempTurn);
					users.set(userPos, tempUser);				
				}
				else {
					System.out.println(2);
					throw new NoAssignedTurnsException();
				}				
			}
			else {
				System.out.println(3);
				throw new NoAssignedTurnsException();
			}
		}		
	}
	/**
	 * Returns the current turn as a String
	 * @return message Message with current Turn as a String
	 */
	public String getCurrentTurnStr() {
		String msg = null;
		if (currentTurn != null) {
			msg = currentTurn.getTurn();
		}
		else {
			msg = "No current turn.";
		}
		return msg;
	}
	/**
	 * Returns document types constants form User class.
	 * @return types Document types in a String array.
	 */
	public String[] getDocumentTypes(){
		String[] types = {User.CEDULA_DE_CIUDADANIA,User.CEDULA_DE_EXTRANJERIA,User.PASSPORT,User.REGISTRO_CIVIL,User.TARJETA_DE_IDENTIDAD};
		return types;
	}	
}
