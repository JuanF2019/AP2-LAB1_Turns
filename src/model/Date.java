package model;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("serial")
public class Date implements Comparable<Date>,Cloneable,Serializable{
	
	private LocalDateTime date;
	
	
	public Date() {
		date = LocalDateTime.now();		
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
	public Date(int y, int m, int d, int h, int min, int s) {
		date = LocalDateTime.of(y, m, d, h, min, s);		
	}	
	
	public String toString() {	
		return date.toString();
	}
	
	public void plusDays(double daysToAdd) {
		double d = daysToAdd*24*60*60;		
		date = date.plusSeconds((long)(d));
	}
	
	public void plusMinutes(double minutesToAdd) {
		double d = minutesToAdd*60;		
		date = date.plusSeconds((long)d);
	}
	
	public static Date now() {
		return new Date();
	}
	
	@Override
	public int compareTo(Date date1) {
		int comparable = 0;
		if(date.isAfter(date1.date)) {
			comparable = 1;
		}else if(date.isBefore(date1.date)) {
			comparable = -1;
		}else {
			comparable = 0;
		}
		return comparable;
	}
	
	
	public double getDifferenceInMinutes(Date date1) {
		return date.until(date1.date, ChronoUnit.MINUTES);
	}
	
	public Date clone() {
		Date clone = null;
        try
        {
            clone = (Date)super.clone();
        } 
        catch(CloneNotSupportedException e)
        {
           
        }
        return clone;
	}
	
	
}
