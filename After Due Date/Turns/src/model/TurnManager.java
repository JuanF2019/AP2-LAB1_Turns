package model;
import customExceptions.*;
import java.util.ArrayList;

public class TurnManager {
	private Turn currentTurn;	
	private ArrayList<User> users;
	private ArrayList<Turn> turns;
	/**
	 * Constructs an empty users list, sets current turn to null and fills turns list with available turns.<br>
	 * <b>POS:</b><br>1. Users list initialized.<br>
	 * 2. Current turn set to null.<br>
	 * 3. Turn list filled with available turns. 
	 */
	public TurnManager() {
		users = new ArrayList<User>();
		turns = new ArrayList<Turn>();
		for(int j = (int)Turn.MIN_LETTER; j <= (int)Turn.MAX_LETTER;j++ ) {
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
		User tempUser;
		Turn tempTurn;
		boolean check = false;
		
		for(int i = 0; i < users.size() && !check; i++) {
			if(users.get(i) != null) {				
				tempUser = users.get(i);
				tempTurn = tempUser.getAssignedTurn();
				
				if (tempTurn != null && tempTurn.isEqualTurn(turn)) {					
					check = true;
					pos = i;									
				}
			}			
		}		
		return pos;
	}
	/**
	 * Finds next turn given its availability, and returns its position, returns -1 if no turn was found.
	 * @return position Position, turn position, -1 if no turn was not found.
	 */
	public int findNextAvailableTurn() {
		int pos = -1;
		Turn tempTurn = null;
		boolean check = false;
		
		if(currentTurn != null && getTurnPos(currentTurn) != turns.size()-1) {
			for(int i = getTurnPos(currentTurn)+1; i < turns.size() && !check; i++) {
				tempTurn = turns.get(i);
				if (tempTurn.isAvailable()) {
					check = true;
					pos = i;
				}
			}
			
			if(pos==-1) {
				for(int i = 0; i < getTurnPos(currentTurn) && !check; i++) {
					tempTurn = turns.get(i);
					if (tempTurn.isAvailable()) {
						check = true;
						pos = i;
					}
				}
			}
		}		
		else {
			for(int i = 0; i < turns.size() && !check; i++) {
				tempTurn = turns.get(i);
				if (tempTurn.isAvailable()) {
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
	public int getTurnPos(Turn turn) {
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
	public String assignTurn(String dT, String dN) throws UserNotFoundException, UserAlreadyHasTurnException{
		User user;
		Turn turn;
		
		int userPos = findUserByDTDN(dT,dN);		
		if (userPos != -1) {
			
			user = users.get(userPos);
			
			int turnPos = findNextAvailableTurn();
			turn = turns.get(turnPos);
			
			if(user.getAssignedTurn() == null && turnPos !=-1) {
				
				turn.setAvailable(false);
				user.setAssignedTurn(turn);				
				
				users.set(userPos,user); //Updates users lists.
								
				turns.set(turnPos,turn); //Updates turns lists.
					
			}
			else {
				throw new UserAlreadyHasTurnException(dT,dN,user.getAssignedTurnStr());
			}
		}	
		else {
			throw new UserNotFoundException(dT,dN);
		}
		return user.getAssignedTurnStr();
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
		
		if(currentTurn == null && !turns.get(0).isAvailable()) {//When there is no current turn.
			tempTurn = turns.get(0);
			
			userPos = findUserByTurn(tempTurn);
			tempUser = users.get(userPos);						
			tempUser.setAssignedTurn(null);
			
			currentTurn = tempTurn;
			System.out.println("userPos = " + userPos);
						
			users.set(userPos, tempUser);
			System.out.println(users.get(0).getName());
			System.out.println(users.get(1).getName());
			System.out.println(users.get(2).getName());
			
		}
		else if(currentTurn != null 
				&& !turns.get(getTurnPos(currentTurn)+1).isAvailable() 
				&& getTurnPos(currentTurn) != turns.size()-1 ) {//When the current turn is not the last.
			//Gets next turn position
			turnPos = getTurnPos(currentTurn) + 1;	
			
			//Sets to available the previous turn.
			tempTurn = turns.get(turnPos-1);
			tempTurn.setAvailable(true);
			//Updates turns.
			
			turns.set(turnPos-1, tempTurn);
			
			//Sets tempTurn to the next turn.
			tempTurn = turns.get(turnPos);
			
			//Gets user that has the next turn (Position and user).
			userPos = findUserByTurn(tempTurn);
			tempUser = users.get(userPos);						
			
			//Sets user assigned turn to null.
			tempUser.setAssignedTurn(null);
			//Updates current turn
			currentTurn = tempTurn;
			//Updates users
			
			users.set(userPos, tempUser);			
		}
		else if(currentTurn != null 
				&& getTurnPos(currentTurn) == turns.size()-1
				&& !turns.get(0).isAvailable()) {//When the current turn is the last.
			//Sets previous turn position
			turnPos = turns.size()-1;	
			
			//Sets to available the previous turn.
			tempTurn = turns.get(turnPos);
			tempTurn.setAvailable(true);
			
			//Updates turns.
			
			turns.set(turnPos-1, tempTurn);
			
			//Sets tempTurn to the next turn.
			tempTurn = turns.get(0);
			
			//Gets user that has the next turn (Position and user).
			userPos = findUserByTurn(tempTurn);
			tempUser = users.get(userPos);						
			
			//Sets user assigned turn to null.
			tempUser.setAssignedTurn(null);
			//Updates current turn
			currentTurn = tempTurn;
			//Updates users			
			users.set(userPos, tempUser);
		}
		else {
			throw new NoAssignedTurnsException();
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
	
	public void setUsers(ArrayList<User> u) {
		users = u;
	}

	public ArrayList<Turn> getTurns() {
		return turns;
	}

	public void setTurns(ArrayList<Turn> turns) {
		this.turns = turns;
	}

	public Turn getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(Turn currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	
	
	
}
