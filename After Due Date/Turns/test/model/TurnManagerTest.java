package model;
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
	void testFindUserByDTDN1() {
		setup3();
		int i = 0;
		try {
			i = tm.findUserByDTDN("CE","3456");
		}
		catch(UserNotFoundException ex){
			fail();
		}			
		assertEquals(1,i);
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
}
