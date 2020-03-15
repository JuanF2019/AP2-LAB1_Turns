package customExceptions;

@SuppressWarnings("serial")
public class UserHasRestrictionException extends Exception {
	public UserHasRestrictionException(double remaining) {
		super("User has restriction, remaining time: " + remaining + " days.");
		}	
}
