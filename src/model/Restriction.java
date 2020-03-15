package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Restriction implements Serializable {
	private double duration;
	
	public Restriction(double d) {
		duration = d*24*60;		
	}

	public double getDuration() {
		return duration;
	}
	
	public void decreaseDuration(double minutes) {
		duration -= minutes;
	}	
	
	public String toString() {
		return "Remainin restriction time: " + (duration/60)/24 + "days";
	}
}
