package customExceptions;

@SuppressWarnings("serial")
public class NoAssignedTurnsException extends Exception {
	/**
	 * Constructs NoAssignedTurnsException when there are no assigned turns.
	 */
	public NoAssignedTurnsException() {
		super("There are no assigned turns, please assign the next turn.");
	}
}
