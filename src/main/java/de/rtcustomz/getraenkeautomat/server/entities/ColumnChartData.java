package de.rtcustomz.getraenkeautomat.server.entities;

public class ColumnChartData {
	Integer timeSpan;
	Integer count;
	String drink;
	
	public ColumnChartData() {
		this.timeSpan = 0;
		this.count = 0;
		this.drink = "";
	}
	
	public ColumnChartData(Integer timeSpan, Long count, String drink) {
		this.timeSpan = timeSpan;
		this.count = count.intValue();
		this.drink = drink;
	}
	
	public Integer getTimeSpan() {
		return timeSpan;
	}
	
	public void setTimeSpan(Integer timeSpan) {
		this.timeSpan = timeSpan;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}
}
