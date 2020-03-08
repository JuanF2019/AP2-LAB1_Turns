package customExceptions;



@SuppressWarnings("serial")
public class InvalidTypeException extends Exception {
	private int type;
	private int maximum;
	
	public InvalidTypeException(int t, int max) {
		super("Invalid type, type should be a value from 0 to " + max + "." );
		type = t;
		maximum = max;
	}

	public int getType() {
		return type;
	}

	public int getMaximum() {
		return maximum;
	}
	
}
