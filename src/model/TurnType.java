package model;
import java.io.Serializable;
@SuppressWarnings("serial")
public class TurnType implements Serializable{
	private String name;
	private float duration;
	/**
	 * Constructs a TurnType given its name and duration
	 * @param n Name
	 * @param d Duration
	 */
	public TurnType(String n, float d) {
		name = n;
		duration = d;
	}
	/**
	 * Returns TurnType name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Return TurnType duration
	 * @return duration
	 */
	public float getDuration() {
		return duration;
	}
	
	public String toString() {
		return name + " - " + duration + " minutes";
	}
}
