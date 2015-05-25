package de.rtcustomz.getraenkeautomat.server.locators;

import com.google.web.bindery.requestfactory.shared.Locator;

import de.rtcustomz.getraenkeautomat.server.daos.UserDAO;
import de.rtcustomz.getraenkeautomat.server.entities.User;

public class UserLocator extends Locator<User, Integer> {

	@Override
	public User create(Class<? extends User> clazz) {
		return new User();
	}

	@Override
	public User find(Class<? extends User> clazz, Integer id) {
		return UserDAO.findById(id);
	}

	@Override
	public Class<User> getDomainType() {
		return User.class;
	}

	@Override
	public Integer getId(User domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		return Integer.class;
	}

	@Override
	public Object getVersion(User domainObject) {
		return domainObject.getVersion();
	}

}
