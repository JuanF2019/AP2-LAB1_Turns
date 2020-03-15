package model;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Turn implements Comparable<Turn>,Serializable {
	public final static int MAX_NUMBER = 99;
	public final static int MIN_NUMBER = 0;
	public final static char MAX_LETTER = 'Z';
	public final static char MIN_LETTER = 'A';
	private char letter;
	private int number;
	private TurnType type;
	
	/**
	 * Constructs a new turn given its letter, number and availability.
	 * @param l Letter
	 * @param n Number
	 * @param a Available
	 */
	public Turn(char l, int n, TurnType t) {
		letter = l;
		number = n;		
		type = t;
	}
	/**
	 * Returns a String representing the turn combining letter and number.
	 * @return turn Turn as a String combining letter and number.
	 */
	public String toString(){
		String n = "";
		if (number<10) {
			n = "0" + number;
		}
		else {
			n =  "" + number;
		}
		return letter + n + " - Type: " + type.getName();
	}	
	/**
	 * Compares turn to a given turn. Only compares letter and number.
	 * @param turn2 Turn to compare.
	 * @return equals True if both letter and number attributes are equal, else is false.
	 */	
	public int compareTo(Turn turn2) {
		int comparable = 0;
		if((int)letter > (int)turn2.getLetter()) {
			comparable = 1;
		}	
		else if((int)letter < (int)turn2.getLetter()) {
			comparable = -1;
		}
		else {
			if(number > turn2.getNumber()) {
				comparable = 1;
			}
			else if(number < turn2.getNumber()) {
				comparable = -1;
			}
			else {
				comparable = 0;
			}
		}
		return comparable;
	}
	/**
	 * Return turn letter.
	 * @return letter Turn letter
	 */
	public char getLetter() {
		return letter;
	}
	/**
	 * Returns turn number.
	 * @return number Turn number
	 */
	public int getNumber() {
		return number;
	}
	/**
	 * 
	 * @param type
	 */
	public void setType(TurnType type) {
		this.type = type;
	}
	/**
	 * 
	 * @return
	 */
	public TurnType getType() {
		return type;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}