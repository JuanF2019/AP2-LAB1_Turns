package customExceptions;

@SuppressWarnings("serial")
public class NoAvailableTurnsException extends Exception {		

	public NoAvailableTurnsException() {
		super("There are no available turns to be assigned.");
	}
}
