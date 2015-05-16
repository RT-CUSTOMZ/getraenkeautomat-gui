package de.rtcustomz.getraenkeautomat.model;

public class Slot {
	private int id;
	private String drink;
	
	public Slot(int id, String drink){
		this.id=id;
		this.drink=drink;
	}
	
	public int getId() {
		return id;
	}
	public String getDrink() {
		return drink;
	}
	
	
}
