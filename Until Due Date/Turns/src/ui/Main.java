package ui;
import model.*;
import customExceptions.*;
import java.util.*;

public class Main {
	private static Scanner inputStr;
	private static Scanner inputNum;
	/**
	 * Executable main method.
	 * @param args
	 */
	public static void main(String[] args) {
		inputNum = new Scanner(System.in);
		int opt = 0;
		boolean menu = true;
		System.out.println("Welcome to turn manager software - Console UI version");
		TurnManager tm = new TurnManager();
		
		while(menu) {
			System.out.println("--------------------\nCurrent turn: " + tm.getCurrentTurnStr());
			System.out.println("--------------------\nMenu:\n");			
			System.out.println("1. Register new user.");
			System.out.println("2. Assign turn to existing user.");
			System.out.println("3. Advance turn.");
			System.out.println("4. Exit.");
			System.out.println("--------------------");
			try {
				opt = inputNum.nextInt();
				switch(opt) {
					case 1:
						tm = registerUser(tm);
					break;
					case 2:
						tm = assignTurn(tm);
					break;
					case 3:
						tm = advanceTurn(tm);
					break;
					case 4:
						menu = false;
						System.out.println("Closing program...");
					break;
					default:
						System.out.println("Invalid option: " + opt);
					
				}
			}
			catch (IllegalArgumentException ex) {
				System.out.println("Invalid input:" + ex.getMessage());
			}			
		}	
	}
	/**
	 * Registers a user.
	 * @param tm TurnManager
	 * @return tm TurnManager
	 */
	public static TurnManager registerUser(TurnManager tm){
		inputStr = new Scanner(System.in);
		
		String data = "";
		String[] splitData;
		boolean check = false;
		
		String[] types = tm.getDocumentTypes();
		System.out.println("Valid document types:");
		for (int i = 0; i<types.length;i++) {
			System.out.printf(types[i] + ";");
		}
		
		System.out.println("\n\nType the user information as follows:");
		System.out.println("NAME;SURNAME;DOCUMENT TYPE;DOCUMENT NUMBER;PHONE NUMBER;ADDRESS\n");
		System.out.println("Note: Add a space between each empty field.\n");
		data = inputStr.nextLine();
		data += " ";
		splitData = data.split(";");
		splitData[0] = splitData[0].toUpperCase();
		splitData[1] = splitData[1].toUpperCase();
		splitData[2] = splitData[2].trim();
		splitData[3] = splitData[3].trim();
		splitData[4] = splitData[4].trim();
		splitData[5] = splitData[5].toUpperCase();
		//Verifies that the input document type is valid.
		for (int i = 0; i<types.length;i++) {
			if(splitData[2].toUpperCase().equals(types[i])){
				check = true;
			}
		}
		//If is empty it lets it through.		
		if (splitData[2].isEmpty()) {
			check = true;
		}
		
				
		if (splitData.length == 6 && check ) {
					
			try {
				tm.addUser(splitData[0], splitData[1], splitData[2], splitData[3], splitData[4], splitData[5]);
				System.out.println("User with document " + splitData[2].toUpperCase() + " " + splitData[3] + " added succesfully.");
			}
			catch (UserAlreadyExistsException ex) {
				System.out.println("Error: " + ex.getMessage());
			}
			catch(MissingInformationException ex){
				System.out.println("Error: " + ex.getCustomMessage());
			}
		}
		else {
			System.out.println("Error: Invalid input format or invalid document type.");
		}	
		return tm;		
	}
	/**
	 * Assigns a turn to a existing user.
	 * @param tm Turn manager
	 * @return tm Turn manager
	 */
	 
	public static TurnManager assignTurn(TurnManager tm){
		inputStr = new Scanner(System.in);		
		boolean check = false;
		String[] types = tm.getDocumentTypes();		
		String data = "";
		String[] splitData;
		
		System.out.println("Valid document types:");
		for (int i = 0; i<types.length;i++) {
			System.out.printf(types[i] + ";");
		}
		
		System.out.println("\nType the user information as follows:");
		System.out.println("DOCUMENT TYPE;DOCUMENT NUMBER\n");
		
		data = inputStr.nextLine();
		data += " ";
		splitData = data.split(";");
		splitData[0] = splitData[0].trim();
		splitData[1] = splitData[1].trim();
		
		//Verifies that the input document type is valid.
		for (int i = 0; i<types.length;i++) {
			if(splitData[0].toUpperCase().equals(types[i])){
				check = true;
			}
		}
		
		//If is empty it lets it through.		
		if (splitData[1].isEmpty()) {
			check = true;
		}
		
		if (splitData.length == 2 && check ) {			
			try {
				System.out.println("Assigned turn for user " + splitData[0].toUpperCase() + " " + splitData[1] + " is: "+ tm.assignTurn(splitData[0], splitData[1]));
				
			}
			catch(UserNotFoundException ex){
				System.out.println("Error: " + ex.getMessage());
			}
		}
		else {
			System.out.println("Error: Invalid input format or invalid document type.");
		}
		return tm;		
	}
	/**
	 * Advance a turn
	 * @param tm Turn Manager
	 * @return tm Turn Manager
	 */
	public static TurnManager advanceTurn(TurnManager tm){
		int opt = 0;
		TurnManager tmSave = tm;
		boolean check = false;
		inputNum = new Scanner(System.in);	
		try {
			tm.advanceTurn();
			tm = tmSave;
			do {
				System.out.println("User is present?");
				System.out.println("1. Yes");
				System.out.println("2. No");
			
				opt = inputNum.nextInt();
				switch(opt) {
					case 1:
						try{			
							tm.advanceTurn();						
						}
						catch(NoAssignedTurnsException ex) {
							System.out.println("Error: " + ex.getMessage());
						}
						finally {
							check = true;
						}
							
					break;
					case 2:
						try{			
							tm.advanceTurn();						
						}
						catch(NoAssignedTurnsException ex) {
							System.out.println("Error: " + ex.getMessage());
							check = true;
						}						
					break;
					default:
				}
			}while(!check);
		}
		catch(NoAssignedTurnsException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
					
		return tm;		
	}
	

}
