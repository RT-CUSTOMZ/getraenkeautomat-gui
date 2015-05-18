package de.rtcustomz.getraenkeautomat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable=false, updatable=false)
	private String first_name;
	
	@Column(nullable=false)
	private String last_name;
	
	private String nickname;
	
	public User() {}

	public User(String first_name, String last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public String getFirstname() {
		return first_name;
	}

	public String getLastname() {
		return last_name;
	}

	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
}
