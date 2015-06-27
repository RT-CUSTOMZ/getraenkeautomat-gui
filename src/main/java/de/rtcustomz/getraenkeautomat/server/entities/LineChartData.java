package de.rtcustomz.getraenkeautomat.server.entities;

public class LineChartData {
	Integer x;
	Integer y;
	String drink;
	
	public LineChartData() {
		this.x = 0;
		this.y = 0;
		this.drink = "";
	}
	
	public LineChartData(Integer x, Long y, String drink) {
		this.x = x;
		this.y = y.intValue();
		this.drink = drink;
	}
	
	public Integer getX() {
		return x;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}
}
