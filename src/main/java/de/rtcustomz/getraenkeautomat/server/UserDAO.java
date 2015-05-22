package de.rtcustomz.getraenkeautomat.server;

import java.util.ArrayList;
import java.util.List;

import de.rtcustomz.getraenkeautomat.model.User;

public class UserDAO {
	public Long countUsers() {
		return 42l;
	}
	
	public List<User> findAllUsers() {
		return new ArrayList<User>();
	}
	
	public List<User> findUserEntries(int firstResult, int maxResults) {
		return new ArrayList<User>();
	}
	
	public User findById(int id) {
		return new User();
	}
	
	public void save(User user) {
		
	}
	
	public User createUser(String first_name, String last_name, String nickname) {
		return new User();
	}

	public void delete(User user) {
		
	}
}
