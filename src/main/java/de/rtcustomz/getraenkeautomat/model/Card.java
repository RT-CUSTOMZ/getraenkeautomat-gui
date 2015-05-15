package de.rtcustomz.getraenkeautomat.model;

import java.sql.Timestamp;
import java.util.Calendar;

public class Card {
	private final String id;
	private final String type;
	private final Timestamp created;
	private String description;
	private User user;

	public Card(String id, String type) {
		this.id = id;
		this.type = type;
		this.created = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}
	
	public Timestamp getCreated() {
		return created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}