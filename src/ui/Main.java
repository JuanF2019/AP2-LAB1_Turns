package ui;

import model.*;
import customExceptions.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
	private static Scanner inputStr;
	private static Scanner inputNum;
	
	public static void main(String[] args){
		inputNum = new Scanner(System.in);		
		int opt = 0;
		boolean menu = true;
		System.out.println("Welcome to turn manager software - Console UI version");
		TurnManager tm;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data//data.dat"));
			tm = (TurnManager)ois.readObject();
			ois.close();
		} 
		catch (IOException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
			tm = new TurnManager();
		}
		
		while(menu) {			
			System.out.println("--------------------\nCurrent turn: " + tm.getCurrentTurnStr());
			System.out.println("Current date: " + tm.getCurrentDate());			
			System.out.println("--------------------\nMenu:\n");			
			System.out.println("1. Add new user.");
			System.out.println("2. Add random users");
			System.out.println("3. Add turn type.");
			System.out.println("4. Assign turn to existing user.");
			System.out.println("5. Assign random turns");
			System.out.println("6. Update date - Manual.");
			System.out.println("7. Update date - Auto.");	
			System.out.println("8. Show current date.");			
			System.out.println("9. Generate user past turns report.");					
			System.out.println("10. Exit.");
			System.out.println("--------------------");
			try {
				opt = inputNum.nextInt();					
				switch(opt) {
					case 1:
						tm = registerUser(tm);
					break;
					case 2:
						tm = addRandomUsers(tm);
					break;
					case 3:
						tm = addTurnType(tm);
					break;
					case 4:
						tm = assignTurn(tm);
					break;
					case 5:
						tm = assignRandomTurns(tm);
					break;
					case 6:
						tm = manualAdvanceTurns(tm);
					break;
					case 7:
						tm = autoAdvanceTurns(tm);
					break;
					case 8:
						System.out.println("Current date: " + tm.getCurrentDate());
					break;
					case 9:
						tm = generateUserPastTurnsReport(tm);
					break;					
					case 10:
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
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data//data.dat"));
			oos.writeObject(tm);
			oos.close();
		} 
		catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public static TurnManager registerUser(TurnManager tm){
		inputStr = new Scanner(System.in);		
		String[] types = tm.getDocumentTypes();
		
		System.out.println("Mandatory (*)");
		System.out.print("*Type name: ");
		String name = inputStr.nextLine();
		
		System.out.print("*Type surname: ");
		String surname = inputStr.nextLine();
		
		System.out.print("*Type document type ");
		System.out.print("( Valid document types - ");
		for (int i = 0; i<types.length;i++) {
			System.out.print("(" +types[i] + ")");
		}		
		System.out.print("): ");
		String documentType = inputStr.nextLine();
		
		System.out.print("*Type document number: ");
		String documentNumber = inputStr.nextLine();
		
		System.out.print(" Type phone number: ");
		String phoneNumber = inputStr.nextLine();
		
		System.out.print(" Type address: ");
		String address = inputStr.nextLine();		
		long t1 = System.currentTimeMillis();
		//Verifies that the input document type is valid.
		boolean check = false;	
		for (int i = 0; i < types.length && !check;i++) {
			if(documentType.toUpperCase().equals(types[i])){
				check = true;
			}
		}						
		
		if(check) {					
			try {
				tm.addUser(name,surname,documentType,documentNumber,phoneNumber,address);
				System.out.println("User with document " + documentType + " " + documentNumber + " added succesfully.");
			}
			catch (UserAlreadyExistsException ex) {
				System.out.println("Error: " + ex.getMessage());
			}
			catch(MissingInformationException ex){
				System.out.println("Error: " + ex.getCustomMessage());
			}
		}
		else {
			System.out.println("Error: Invalid document type.");
		}	
		long t2 = System.currentTimeMillis();
		System.out.println("Operation took " + (t2-t1) + "ms");
		return tm;		
	}
		 
	public static TurnManager assignTurn(TurnManager tm){
		inputStr = new Scanner(System.in);					
		ArrayList<String> turnTypes = tm.getTypes();
		long t1 = System.currentTimeMillis();
		if(!turnTypes.isEmpty()) {
			System.out.print("*Type document number: ");
			String documentNumber = inputStr.nextLine();
			
			System.out.print("*Type turn type ");			
			for (int i = 0; i<turnTypes.size();i++) {
				System.out.print((i+1) + ". " + turnTypes.get(i) );
			}				
			String turnType = inputStr.nextLine();
			
			t1 = System.currentTimeMillis();							
			try {
				String turn = tm.assignTurn(documentNumber,Integer.parseInt(turnType)-1);
				System.out.println("Assigned turn for user " + documentNumber + " is: " + turn);					
			}
			catch(UserNotFoundException ex){
				System.out.println("Error: " + ex.getMessage());
			}
			catch(UserAlreadyHasTurnException ex){
				System.out.println("Error: " + ex.getMessage());
			}
			catch(NumberFormatException ex) {
				System.out.println("Error: " + ex.getMessage());
			}
			catch(InvalidTypeException ex) {
				System.out.println("Error: " + ex.getMessage());
			} 
			catch(NoAvailableTurnsException ex) {
				System.out.println("Error: " + ex.getMessage());
			} 
			catch (UserHasRestrictionException ex) {
				System.out.println("Error: " + ex.getMessage());
			}
						
		}
		else {
			System.out.println("Error: No turn types added.");
		}
		long t2 = System.currentTimeMillis();
		System.out.println("Operation took " + (t2-t1) + "ms");
		return tm;		
	}
		
	public static TurnManager addTurnType(TurnManager tm) {
		inputStr = new Scanner(System.in);
		
		System.out.print("Type name:");
		String name = inputStr.nextLine();
				
		System.out.print("Type duration:");
		String duration = inputStr.nextLine();
				
		try {
			tm.addType(name, Float.parseFloat(duration));	
			System.out.println("Turn type added succesfully.");
		}
		catch(NumberFormatException ex) {
			System.out.println("Error: " + ex.getMessage());
		}		
		return tm;
	}

	public static TurnManager autoAdvanceTurns(TurnManager tm) {
		long t1 = System.currentTimeMillis();
		try{
			tm.autoAdvanceTurns();
		}
		catch(NoAssignedTurnsException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		long t2 = System.currentTimeMillis();
		System.out.println("Operation took " + (t2-t1) + "ms");
		return tm;
	}

	public static TurnManager manualAdvanceTurns(TurnManager tm) {
		inputStr = new Scanner(System.in);
		long t1 = 0;
		long t2 = 0;					
		try {
			System.out.println("Type the number of minutes to advance time");
			String minutes = inputStr.nextLine();
			t1 = System.currentTimeMillis();			
			tm.advanceTurns(Double.parseDouble(minutes));
			
		} 
		catch (NumberFormatException | NoAssignedTurnsException ex) {
			t1 = System.currentTimeMillis();	
			System.out.println("Error: " + ex.getMessage());
		}
		t2 = System.currentTimeMillis();
		System.out.println("Operation took " + (t2-t1) + "ms");
		return tm;
	}

	public static TurnManager addRandomUsers(TurnManager tm) {
		inputStr = new Scanner(System.in);
				
		try {
			System.out.print("Type the number of users to generate: ");
			String number = inputStr.nextLine();
			int n = Integer.parseInt(number);
			long t1 = System.currentTimeMillis();
			tm.generateRandomUsers(n);
			long t2 = System.currentTimeMillis();
			System.out.println("Operation took " + (t2-t1) + "ms");
			
		} catch (NumberFormatException | IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		} 
			
		return tm;
	}
	
	public static TurnManager assignRandomTurns(TurnManager tm) {
		inputStr = new Scanner(System.in);
		long t1 = 0;
		long t2 = 0;
		try {
			System.out.println("Type number of turns to generate: ");
			int number = Integer.parseInt(inputStr.nextLine());
			t1 = System.currentTimeMillis();
			System.out.println(tm.generateRandomTurns(number));
			t2 = System.currentTimeMillis();
			System.out.println("Operation took " + (t2-t1) + "ms");
		}
		catch(NumberFormatException ex) {
			t2 = System.currentTimeMillis();
			System.out.println("Operation took " + (t2-t1) + "ms");
		}		
		return tm;
		
	}

	public static TurnManager generateUserPastTurnsReport(TurnManager tm) {
		inputStr = new Scanner(System.in);
		
		System.out.println("Type user document number");
		String dN = inputStr.nextLine();
		long t1 = 0;
		try {
			String report = tm.getUserPastTurns(dN);
			System.out.println("Save to report1.txt?");
			System.out.println("(Type anything to save, leave blanck to print in console)");
			if(!inputStr.nextLine().isEmpty()) {
				BufferedWriter bw = new BufferedWriter(new FileWriter("data//report1.txt"));
				bw.write(report);
				bw.close();
				System.out.println("Saved succesfully!");
			}
			else {
				System.out.println(report);
			}
			long t2 = System.currentTimeMillis();
			System.out.println("Operation took " + (t2-t1) + "ms");			
		}
		catch (UserNotFoundException ex) {
			System.out.println("Error: " + ex.getMessage());
			long t2 = System.currentTimeMillis();
			System.out.println("Operation took " + (t2-t1) + "ms");
		} 
		catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			long t2 = System.currentTimeMillis();
			System.out.println("Operation took " + (t2-t1) + "ms");
		}			
		return tm;
	}
}
