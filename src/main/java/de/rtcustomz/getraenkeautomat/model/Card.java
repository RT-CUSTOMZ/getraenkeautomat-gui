package de.rtcustomz.getraenkeautomat.model;

public class Card {
	private final String id;
	private final String type;
	private String description;
	private int user_id;

	public Card(String id, String type, String description, int user_id) {
		this.id = id;
		this.type = type;
		this.description = description;
		this.user_id = user_id;
	}
	
	public Card(String id, String type) {
		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public int getUserId() {
		return user_id;
	}
}