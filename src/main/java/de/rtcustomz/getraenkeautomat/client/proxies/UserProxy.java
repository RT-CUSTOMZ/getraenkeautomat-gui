package de.rtcustomz.getraenkeautomat.client.proxies;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import de.rtcustomz.getraenkeautomat.server.entities.User;
import de.rtcustomz.getraenkeautomat.server.locators.UserLocator;

@ProxyFor(value = User.class, locator = UserLocator.class)
public interface UserProxy extends EntityProxy {
	public String getFirstname();

	public String getLastname();

	public String getNickname();
	
	public void setNickname(String nickname);

	public int getId();

	public void setId(int id);

	public void setFirstname(String first_name);

	public void setLastname(String last_name);
}
