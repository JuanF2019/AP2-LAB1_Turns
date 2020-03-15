package model;

import customExceptions.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TurnManager implements Serializable {	
	private static final long serialVersionUID = 1989870631771348650L;
	private Turn currentTurn;	
	private ArrayList<User> users;
	private ArrayList<Turn> turns;
	private ArrayList<String> rawTurns;
	private ArrayList<TurnType> types;
	private Date currentSystemDate;
	private int timeDifference;
	private int rawTurnIndex;
		
	public TurnManager() {
		currentTurn = null;
		users = new ArrayList<User>();
		turns = new ArrayList<Turn>();		
		types = new ArrayList<TurnType>();
		currentSystemDate = Date.now();
		timeDifference = 0;
		rawTurnIndex = 0;
		rawTurns = new ArrayList<String>();
		for(int j = (int)Turn.MIN_LETTER; j <= (int)Turn.MAX_LETTER;j++ ) {
			for (int k = Turn.MIN_NUMBER; k <= Turn.MAX_NUMBER; k++) {
				rawTurns.add((char)j + ";" + k);//
			}
		}
	}		
	
	public void addUser(String n, String s, String dT, String dN, String p,	String a) throws UserAlreadyExistsException, MissingInformationException{
		try {	
			findUserByDN(dN);//Throws UserNotFoundException,if is thrown then user does not exist and can be added. 
			throw new UserAlreadyExistsException(dN);			
		}
		catch(UserNotFoundException ex){
			if(n.equals("") || s.equals("") || dT.equals("") || dN.equals("") ) {
				throw new MissingInformationException(n,s,dT,dN);
			}
			users.add(new User(n,s,dT,dN,p,a));
		}
		Collections.sort(users,Collections.reverseOrder(new DNComparator()));
	}
	
	public int findUserByDN(String dN) throws UserNotFoundException{				
				
		bubbleSortUsersByDN();
		
		int low = 0;
		int high = users.size()-1;
		int mid = (low + high)/2;
		boolean check = false;
		while (low <= high && !check) {
			mid = (low + high)/2;
			if( dN.compareTo(users.get(mid).getDocumentNumber())> 0){
				low = mid + 1;
			}
			else if(dN.compareTo(users.get(mid).getDocumentNumber()) < 0) {
				high = mid - 1;
			}
			else if(dN.compareTo(users.get(mid).getDocumentNumber()) == 0){
				check = true;
			}
		}		
		
		if (!check) {			
			throw new UserNotFoundException(dN);			
		}
		else {	
			return mid;
		}
	}
	
	public void bubbleSortUsersByDN() {
		User save;
		DNComparator comparator = new DNComparator();
		boolean exchange = true;
		
		while(exchange) {
			exchange = false;
			for (int i = 0; i < users.size()-1;i++) {
				if (comparator.compare(users.get(i), users.get(i+1)) > 0) {
					save = users.get(i);
					users.set(i,users.get(i+1));					
					users.set(i+1,save);
					exchange = true;
				}
			}
		}
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
				
				if (tempTurn != null && tempTurn.compareTo(turn) == 0) {					
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
	 * 
	 * @param description
	 * @param duration
	 */
	public void addType(String description,float duration) {
		types.add(new TurnType(description,duration));
	}
	
	/**
	 * Assigns a turn to a existing user.
	 * @param dT Document type
	 * @param dN Document number
	 * @return message Message with turn represented as a String.
	 * @throws UserNotFoundException If the user with the given document type and number was not found.
	 * @throws
	 * @throws
	 */
	public String assignTurn(String dN, int type) throws NoAvailableTurnsException, UserNotFoundException, UserAlreadyHasTurnException, InvalidTypeException, UserHasRestrictionException{
		User user;
		Turn turn;
		
		int userPos = findUserByDN(dN);		
		user = users.get(userPos);
			
		if(rawTurns.get(rawTurnIndex) == null) {
			throw new NoAvailableTurnsException();
		}		
		
		if(user.getAssignedTurn() == null && user.getRestrictionDuration() == 0) {
			if(type >= 0 && type < types.size()) {
				String[] splitTurn = rawTurns.get(rawTurnIndex).split(";");
				turn = new Turn(splitTurn[0].charAt(0),Integer.parseInt(splitTurn[1]),types.get(type));	
				user.addPastTurn(turn.toString());
				turns.add(turn);					
				user.setAssignedTurn(turn);										
			}
			else {
				throw new InvalidTypeException(type,types.size());
			}
				
		}
		else if(user.getRestrictionDuration()>0) {
			throw new UserHasRestrictionException(user.getRestrictionDuration());
		}
		else {
			throw new UserAlreadyHasTurnException(users.get(userPos).getDocumentNumber(),dN,user.toStringTurn());
		}
		
		
		if(rawTurnIndex == rawTurns.size()) {
			rawTurnIndex = 0;
		}
		else {
			rawTurnIndex++;
		}	
		return user.toStringTurn();		
	}
	/**
	 * Advances turn to the next one if the next turn is assigned.<br>
	 * <b>POS:</b><br>1. Updates currentTurn with next turn. <br>2. Updates turns and user lists.
	 * @throws NoAssignedTurnsException If there are no assigned turns.
	 */
	public void advanceTurns (double minutes) throws NoAssignedTurnsException{		
		if(!turns.isEmpty()) {
			boolean check = false;
			double c = 0;
			for(int i = 0; i < turns.size() && !check; i++) {
				if(c + turns.get(i).getType().getDuration() < minutes) {		
					int present = (int) Math.round(Math.random());
					User user = users.get(findUserByTurn(turns.get(i)));
					user.setAssignedTurn(null);
					boolean[] attended = user.getAttended();
					
					if (present == 0) {						
						if(attended[0] == false) {
							attended[1] = false;							
							user.setRestriction(2);							
						}
						else {
							attended[0] = false;
						}
					}
					else {
						if(attended[0] == true) {
							attended[1] = true;
							
						}
						else {
							attended[0] = true;
						}
					}	
					if(user.getRestrictionDuration() < 0) {
						user.setRestriction();
					}
					currentTurn = turns.get(i);
					c += turns.get(i).getType().getDuration();
					turns.remove(0);					
				}
				else {
					check = true;
				}
			}		
			
		}
		else {
			timeDifference += minutes;
			throw new NoAssignedTurnsException();
		}
		timeDifference += minutes;
	}
	/**
	 * Returns the current turn as a String
	 * @return message Message with current Turn as a String
	 */
	public String getCurrentTurnStr() {
		String msg = null;
		if (currentTurn != null) {
			msg = currentTurn.toString();
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
	
	public void autoAdvanceTurns() throws NoAssignedTurnsException{	
		Date date = Date.now();
		double dif = currentSystemDate.getDifferenceInMinutes(date);
		currentSystemDate = date;
		timeDifference -= dif;
		advanceTurns(dif);			
	}	

	public ArrayList<String> getRawTurns() {
		return rawTurns;
	}
	
	public String getCurrentDate() {
		Date date = currentSystemDate.clone();
		date.plusMinutes(timeDifference);		
		return date.toString();		
	}
	
	public ArrayList<String> getTypes() {
		ArrayList<String> strTypes = new ArrayList<String>();
		for(int i = 0; i < types.size(); i++) {
			strTypes.add(types.get(i).toString());
		}
		return strTypes;
	}
	
	public void generateRandomUsers(int n) throws IOException {	
		int start = 0;		
		
		if(!users.isEmpty() && users.size()>1) {
			users.sort(new Comparator<User>() {
				@Override
				public int compare(User user1, User user2) {
					int compare = 0;
					if(user1.getDocumentNumber().compareTo(user2.getDocumentNumber()) > 1) {
						compare = 1;
					}
					else if(user1.getDocumentNumber().compareTo(user2.getDocumentNumber()) < 1) {
						compare = -1;
					}
					else {
						compare = 0;
					}
					return compare;
				}
				
			});
			start = Integer.parseInt(users.get(users.size()-1).getDocumentNumber())+1;			
		}			
		
		String[] types = getDocumentTypes();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> surnames = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("data//names.csv"));
		String line = br.readLine();
		while(line != null){
			names.add(line);
			line = br.readLine();
		}		
		
		br.close();
		br = new BufferedReader(new FileReader("data//surnames.csv"));
		line = br.readLine();
		while(line != null){
			surnames.add(line);
			line = br.readLine();
		}		
		
		br.close();		
		
		for(int i = start; i < n + start;i++) {					
			users.add(new User(names.get((int)Math.round(Math.random()*999)),
					surnames.get((int)Math.round(Math.random()*999)),
					types[((int)Math.round(Math.random()*4))],
					i + "", 
					"", 
					""));
			
		}		
		
	}
	
	public String generateRandomTurns(int number) {
		boolean check = false;
		String msg = null;
		if(!types.isEmpty()) {
			int i = 0;
			while(i < users.size() && i < number && !check){			
				System.out.println("i = " + i);
				try {
					assignTurn(users.get(i).getDocumentNumber(),
							(int)(Math.random()*types.size()));
				}
				catch(NoAvailableTurnsException ex){					
					check = true;
				}
				catch(UserAlreadyHasTurnException ex){					
					number++;
				}
				catch(InvalidTypeException | UserNotFoundException | UserHasRestrictionException ex) {
					//Shouldn't enter here.				
				}
				i++;
			}
			msg = i + " turns generated!";
		}
		else {
			msg = "0 turns generated!";
		}
		return msg;
	}
	
	public String getUserPastTurns(String dN) throws UserNotFoundException {
		int userPos = findUserByDN(dN);		
		return users.get(userPos).getPastTurns();		
	}
	
	public void insertionSortUsersByName() {		
		User save;
		for (int i = 0; i < users.size();i++) {
			for (int j = i; j > 0 && users.get(j).getName().compareTo(users.get(j-1).getName()) < 0;j--) {
				save = users.get(j-1);		
				users.set(j-1, users.get(j));
				users.set(j, save);				
			}
		}		
	}
	
	public void selectionSortBySurname() {		
		User minValue = users.get(0);	
		int minPos = 0;
		User save;
		for (int j = 0; j < users.size();j++) {
			minValue = users.get(j);		
			for (int i = j; i < users.size();i++) {
				if(minValue.getSurname().compareTo(users.get(i).getSurname()) >= 0) {			
					minValue = users.get(i);
					minPos = i;					
				}
			}
			save = users.get(j);	
			users.set(j, users.get(minPos));
			users.set(minPos,save);			
		}
		
	}
}
