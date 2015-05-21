package de.rtcustomz.getraenkeautomat.server;

import java.util.ArrayList;
import java.util.List;

import de.rtcustomz.getraenkeautomat.model.User;

public class UserDAO {
	public static Long countUsers() {
		return 42l;
	}
	
	public static List<User> findAllUsers() {
		return new ArrayList<User>();
	}
	
	public static List<User> findUserEntries(int firstResult, int maxResults) {
		return new ArrayList<User>();
	}
	
	public static User findUser(int id) {
		return new User();
	}
	
	public static void save(User User) {
		
	}
	
	public static User createUser(String id, String type) {
		return new User();
	}

	public static void delete(User User) {
		
	}
}
