package model;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTime{
	
	private LocalDate date;
	private LocalTime time;
	
	public DateTime() {
		date = LocalDate.now();
		time = LocalTime.now();
	}
	/**
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @param h
	 * @param min
	 * @param s
	 */
	public DateTime(int y, int m, int d, int h, int min, int s) {
		date = LocalDate.of(y, m, d);
		time = LocalTime.of(h, min, s);
	}	
	
	public String toString() {	
		return date.getDayOfMonth() + "/" 
		+ date.getMonthValue() 
		+ "/" + date.getYear()
		+ " - " + time.getHour() 
		+ ":" + time.getMinute() 
		+ ":" + time.getSecond(); 
	}
	
	public void plusDays(long daysToAdd) {
		date.plusDays(daysToAdd);
	}
	
	public static DateTime now() {
		return new DateTime();
	}
	
	
	
}
