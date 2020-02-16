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
	
}
