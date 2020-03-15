package model;
import java.io.IOException;
import java.util.*;
import customExceptions.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TurnManagerTest {
	TurnManager tm;
	void setup1() {
		tm = new TurnManager();
		try {
			tm.addUser("Juan","Martinez","CC","4567","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
	}	
	void setup2() {
		tm = new TurnManager();		
	}
	void setup3() {
		tm = new TurnManager();
		try {
			tm.addUser("Juan","Martinez","CC","1234","","");
			tm.addUser("Pepito","Perez","CE","3456","","");
			tm.addUser("Lucas","Garcia","CC","5862","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
	}
	
	void setup4() {
		tm = new TurnManager();		
		try {
			tm.addUser("Juan","Martinez","CC","1234","","");
			tm.addUser("Pepito","Perez","CE","3456","","");
			tm.addUser("Lucas","Garcia","CC","5862","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
		
		ArrayList<Turn> turns = tm.getTurns();
		Turn tempTurn;
		ArrayList<User> users = tm.getUsers();
		User tempUser;
		tempUser = users.get(0);
		tempTurn = turns.get(0);
		
		tempTurn.setAvailable(false);
		tempUser.setAssignedTurn(tempTurn);
		
		tm.setUsers(users);
		tm.setTurns(turns);			
	}
	
	void setup5() {
		tm = new TurnManager();		
		try {
			tm.addUser("Juan","Martinez","CC","1234","","");
			tm.addUser("Pepito","Perez","CE","3456","","");
			tm.addUser("Lucas","Garcia","CC","5862","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
		
		tm.addType("Lunch", 5);
		tm.addType("Dinner", 3);
		
	}
	
	@Test
	void testAssignTurn3() {
		setup5();
		
		
		
		try {
			tm.assignTurn("CC","1234", 1);
			tm.assignTurn("CE","3456", 0);
		}
		catch(UserNotFoundException ex) {
			fail();
		}
		catch(UserAlreadyHasTurnException ex){
			fail();
		}
		catch(InvalidTypeException ex) {
			fail();
		} 
		catch (NoAvailableTurnsException e) {
			fail();
		}
		
		ArrayList<User> users = tm.getUsers();
		User user = users.get(0);
		Turn turn = user.getAssignedTurn();
		TurnType type = turn.getType();
		
		assertEquals(type.getName(),"Dinner");
		
		user = users.get(1);
		turn = user.getAssignedTurn();
		type = turn.getType();
		
		assertEquals(type.getName(),"Lunch");
		
	}
	
	@Test
	void testAddUser1() {
		setup1();
		try {
			tm.addUser("Lucas","Garcia","CC","8888","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
		
		ArrayList<User> users;
		users = tm.getUsers();
		User user = users.get(1);
		
		
		assertEquals("8888",user.getDocumentNumber());
	}
	
	
	
	@Test
	void testAddUser2() {
		setup1();
		try {
			tm.addUser("Juan","Martinez","CC","4567","","");
		}
		catch(UserAlreadyExistsException ex) {
			assertTrue(true);
		}
		catch(MissingInformationException ex){
			
		}		
		
	}
	
	@Test
	void testAddUser3() {
		setup2();
		try {
			tm.addUser("Lucas","Garcia","CC","8888","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
		
		ArrayList<User> users;
		users = tm.getUsers();
		User user = users.get(0);
		
		
		assertEquals("8888",user.getDocumentNumber());
		
	}
	
	@Test
	void testAddUser4() {
		setup3();
		try {
			tm.addUser("Carlos","Benitez","CC","8888","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
		
		ArrayList<User> users;
		users = tm.getUsers();
		User user = users.get(3);
		
		
		assertEquals("8888",user.getDocumentNumber());
		
	}
	
	@Test
	void testFindUserByDN1() {
		setup3();
		int i = 0;
		try {
			i = tm.findUserByDN("3456");
		}
		catch(UserNotFoundException ex){
			fail();
		}			
		assertEquals(1,i);
	}
	
	@Test
	void testFindUserByDN2() {
		setup2();		
		try {
			int i = tm.findUserByDN("3456");
			System.out.println(i);
		}
		catch(UserNotFoundException ex){
			assertTrue(true);
		}		
	}
	
	void setup11() {
		tm = new TurnManager();
		try {
			tm.addUser("Juan","Martinez","CC","7234","","");
			tm.addUser("Pepito","Perez","CE","3456","","");
			tm.addUser("Lucas","Garcia","CC","2862","","");
		}
		catch(UserAlreadyExistsException ex) {
			
		}
		catch(MissingInformationException ex){
			
		}
	}
	@Test
	void testFindUserByDN3() {
		setup11();		
		try {
			int i = tm.findUserByDN("3456");
			assertEquals(1,i);
			i = tm.findUserByDN("2862");
			assertEquals(0,i);
		}
		catch(UserNotFoundException ex){
			fail();			
		}		
		
	}
	@Test
	void testBubbleSortByDN() {
		setup11();
		tm.bubbleSortUsersByDN();
		ArrayList<User> users = tm.getUsers();				
		assertEquals("2862",users.get(0).getDocumentNumber());
		assertEquals("3456",users.get(1).getDocumentNumber());
		assertEquals("7234",users.get(2).getDocumentNumber());
	}
	void setup10() {
		tm = new TurnManager();
	}
	@Test
	void testGenerateRandomTurns2() {
		setup10();
		tm.addType("Lunch", 2);
		tm.addType("Dinner", 3);
		try {
			tm.generateRandomUsers(10);
		} catch (IOException e) {
			fail();
		}
		tm.generateRandomTurns(10);
		
		ArrayList<User> users = tm.getUsers();		
		for(int i = 0; i<users.size();i++) {
			
			System.out.println(users.get(i).getName() + " - " + users.get(i).toStringTurn());
		}
		
	}
	@Test
	void testGenerateRandomUsers() {
		setup10();
		try {
			tm.generateRandomUsers(10);
			tm.generateRandomUsers(10);
		} catch (IOException e) {
			fail();
		}
		
		ArrayList<User> users = tm.getUsers();		
		for(int i = 0; i<users.size();i++) {
			System.out.println(users.get(i).getName() + " - " + users.get(i).getDocumentNumber());
		}
	}
	
	@Test 
	void testGenerateRandomTurns() {
		
	}
	
	
	
	@Test
	void testFindUserByTurn1() {
		setup3();
		ArrayList<User> users = tm.getUsers();
		ArrayList<Turn> turns = tm.getTurns();
		
		User user = users.get(1);
		Turn turn = turns.get(0);
		
		turn.setAvailable(false);	
		
		user.setAssignedTurn(turn);
		
		users.set(1,user);
		turns.set(0,turn);
		
		assertEquals(1,tm.findUserByTurn(turn));		
	}
	
	@Test
	void testFindNextAvailableTurn1() {
		setup3();
		int i = tm.findNextAvailableTurn();
		assertEquals(0,i);		
	}
	@Test
	void testFindNextAvailableTurn2() {
		setup3();
		
		ArrayList<Turn> turns = tm.getTurns();
		Turn turn = turns.get(0);
		
		turn.setAvailable(false);
		turns.set(0,turn);
		
		int i = tm.findNextAvailableTurn();
		assertEquals(1,i);		
	}
	@Test
	void testFindNextAvailableTurn3() {
		setup3();		
		
		ArrayList<Turn> turns = tm.getTurns();
		
		Turn turn = turns.get(turns.size()-1);
		turn.setAvailable(false);		
		tm.setCurrentTurn(turn);
		turns.set(turns.size()-1,turn);
		
		tm.setTurns(turns);
		
		int i = tm.findNextAvailableTurn();
		assertEquals(0,i);		
	}
	
	@Test
	void testFindNextAvailableTurn4() {
		setup3();		
		
		ArrayList<Turn> turns = tm.getTurns();
		
		Turn turn = turns.get(turns.size()-1);		
		turn.setAvailable(false);		
		tm.setCurrentTurn(turn);
		turns.set(turns.size()-1,turn);
		
		turn = turns.get(0);
		turn.setAvailable(false);		
		turns.set(0,turn);
		
		
		
		tm.setTurns(turns);
		
		int i = tm.findNextAvailableTurn();
		assertEquals(1,i);		
	}
	
	@Test
	void testFindNextAvailableTurn5() {
		setup3();		
		
		ArrayList<Turn> turns = tm.getTurns();
		
		Turn turn = null;
		
		for (int i = 0; i < turns.size();i++) {
			turn = turns.get(i);
			turn.setAvailable(false);
			turns.set(i,turn);
		}		
		tm.setTurns(turns);
		
		int i = tm.findNextAvailableTurn();
		assertEquals(-1,i);		
	}
	
	@Test
	void testFindNextAvailableTurn6() {
		setup3();		
		
		ArrayList<Turn> turns = tm.getTurns();
		
		Turn turn = null;
		
		for (int i = 5; i < turns.size();i++) {
			turn = turns.get(i);
			turn.setAvailable(false);
			turns.set(i,turn);
		}		
		
		turn = turns.get(turns.size()-2);		
		tm.setCurrentTurn(turn);
		turns.set(turns.size()-2,turn);
		
		tm.setTurns(turns);
		
		int i = tm.findNextAvailableTurn();
		assertEquals(0,i);		
	}
	@Test
	void testGetTurnPos() {
		setup2();
		ArrayList<Turn> turns = tm.getTurns();
		Turn turn = turns.get(99);
		
		int i = tm.getTurnPos(turn);
		
		assertEquals(99,i);		
	}
	/*
	@Test
	void testAssignTurn1() {
		setup3();
		
		try {
			tm.assignTurn("CC", "1234");
		}
		catch(UserNotFoundException ex) {
			fail();
		}
		catch(UserAlreadyHasTurnException ex) {
			fail();
		}
		ArrayList<User> users = tm.getUsers();
		ArrayList<Turn> turns = tm .getTurns();
		
		assertEquals("A00",(users.get(0)).getAssignedTurnStr());		
		assertEquals(false,turns.get(0).isAvailable());
	}
	
	@Test
	void testAssignTurn2() {
		setup4();
		
		try {
			tm.assignTurn("CE", "3456");
		}
		catch(UserNotFoundException ex) {
			fail();
		}
		catch(UserAlreadyHasTurnException ex) {
			fail();
		}
		
		ArrayList<User> users = tm.getUsers();
		ArrayList<Turn> turns = tm .getTurns();
		
		assertEquals("A01",(users.get(1)).getAssignedTurnStr());		
		assertEquals(false,turns.get(1).isAvailable());		
	}
	*/
	
	@Test
	void testTurnManager() {
		tm = new TurnManager();
		
		ArrayList<String> rawTurns = tm.getRawTurns();
		
		for(int i =0; i<rawTurns.size(); i++) {
			System.out.println(rawTurns.get(i));
		}
		
		
		
	}
	
	@Test
	void testAssignTurn() {
		setup5();
		
		try {
			tm.assignTurn("CC", "1234", 0);
			tm.assignTurn("CE", "3456", 0);
		} catch (NoAvailableTurnsException | UserNotFoundException | UserAlreadyHasTurnException
				| InvalidTypeException e) {
			fail();
		}
		
		ArrayList<Turn> turns = tm.getTurns();
		
		
		
		assertEquals(turns.get(0).toString(),"A00 - Type: Lunch");
		assertEquals(turns.get(1).toString(),"A01 - Type: Lunch");
		
		
	}
}
