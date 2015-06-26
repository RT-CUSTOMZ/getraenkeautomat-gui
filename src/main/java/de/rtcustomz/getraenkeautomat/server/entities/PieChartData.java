package de.rtcustomz.getraenkeautomat.server.entities;

public class PieChartData {
	String drink;
	long count;
	
	public PieChartData() {
		this.drink = "";
		this.count = 0;
	}
	
	public PieChartData(String drink, long count) {
		this.drink = drink;
		this.count = count;
	}
	
	public String getDrink() {
		return drink;
	}
	
	public void setDrink(String drink) {
		this.drink = drink;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}
	
}
