package model;

public class Restriction {
	private int duration;//In days.
	private DateTime limitDateTime;
	
	public Restriction(int d, DateTime currentTime) {
		duration = d;
		currentTime.plusDays(d);
		limitDateTime = currentTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String toString() {
		return "Current restriction for " + duration + " days, until " + limitDateTime.toString();
	}
}
