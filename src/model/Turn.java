package model;

public class Turn {
	public final static int MAX_NUMBER = 99;
	public final static int MIN_NUMBER = 0;
	public final static char MAX_LETTER = 'Z';
	public final static char MIN_LETTER = 'A';
	private char letter;
	private int number;
	private TurnType type;
	private boolean available;
	/**
	 * Constructs a new turn given its letter, number and availability.
	 * @param l Letter
	 * @param n Number
	 * @param a Available
	 */
	public Turn(char l, int n, boolean a, TurnType t) {
		letter = l;
		number = n;
		available = a;
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
		return letter + n + "- Type: " + type.getName();
	}
	/**
	 * Returns turn availability.
	 * @return available Turn availability
	 */
	public boolean isAvailable() {
		return available;
	}
	/**
	 * Sets turn availability to the given value.
	 * @param available Turn availability
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	/**
	 * Compares turn to a given turn. Only compares letter and number.
	 * @param turn2 Turn to compare.
	 * @return equals True if both letter and number attributes are equal, else is false.
	 */	
	public boolean isEqualTurn(Turn turn2) {
		boolean equals = false;
		if(letter == turn2.getLetter() && number == turn2.getNumber()) {
			equals = true;
		}		
		return equals;
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
	public void setType(TurnType type) {
		this.type = type;
	}
	public TurnType getType() {
		return type;
	}
}