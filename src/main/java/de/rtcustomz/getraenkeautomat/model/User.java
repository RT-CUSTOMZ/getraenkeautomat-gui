package de.rtcustomz.getraenkeautomat.model;

public class User {
	private final String first_name;
	private final String last_name;
	private String nickname;

	public User(String first_name, String last_name, String nickname) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.nickname = nickname;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
