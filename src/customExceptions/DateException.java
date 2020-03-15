package customExceptions;
import model.Date;

@SuppressWarnings("serial")
public class DateException extends Exception {
	private Date currentDate;
	private Date date1;

	public DateException(Date currentDate, Date date1 ) {
		super("Error trying to update current date and time, given date" + date1.toString() + "is not after current date " + currentDate.toString());
		this.currentDate = currentDate;
		this.date1 = date1;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public Date getDate1() {
		return date1;
	}
	
}
