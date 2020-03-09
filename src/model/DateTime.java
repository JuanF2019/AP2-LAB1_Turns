package model;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTime implements Comparable<DateTime>{
	
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
	
	public void plusMinutes(long minutesToAdd) {
		date.plusDays(minutesToAdd*(1/60)*24);
	}
	
	public static DateTime now() {
		return new DateTime();
	}
	
	private LocalDate getDate() {
		return date;
	}
	
	private LocalTime getTime() {
		return time;
	}
	
	@Override
	public int compareTo(DateTime dateTime1) {
		int comparable = 0;
		if(date.isAfter(dateTime1.getDate())) {
			comparable = 1;
		}else if(date.isBefore(dateTime1.getDate())) {
			comparable = -1;
		}else {
			if(time.isAfter(dateTime1.getTime())) {
				comparable = 1;
			}
			else if(time.isBefore(dateTime1.getTime())) {
				comparable = -1;
			}
			else {
				comparable = 0;
			}
		}
		return comparable;
	}
	
	
	
}
