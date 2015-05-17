package de.rtcustomz.getraenkeautomat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="slots")
public class Slot {
	@Id
	private int id;
	
	@Column(nullable=false)
	private String drink;
	
	public Slot() {}
	
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

	public void setId(int id) {
		this.id = id;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}
}
