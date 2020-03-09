package customExceptions;
import model.DateTime;

@SuppressWarnings("serial")
public class DateTimeException extends Exception {
	private DateTime currentDateTime;
	private DateTime dateTime1;

	public DateTimeException(DateTime currentDateTime, DateTime dateTime1 ) {
		super("Error trying to update current date and time, given date" + dateTime1.toString() + "is not after current date " + currentDateTime.toString());
		this.currentDateTime = currentDateTime;
		this.dateTime1 = dateTime1;
	}

	public DateTime getCurrentDateTime() {
		return currentDateTime;
	}

	public DateTime getDateTime1() {
		return dateTime1;
	}
	
}
