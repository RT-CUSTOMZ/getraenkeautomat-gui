package de.rtcustomz.getraenkeautomat.client;

import java.sql.Timestamp;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import de.rtcustomz.getraenkeautomat.model.Card;
import de.rtcustomz.getraenkeautomat.model.CardLocator;

@ProxyFor(value = Card.class, locator = CardLocator.class)
public interface CardProxy extends EntityProxy {
	public String getId();
	
	public void setId(String id);

	public String getType();

	public void setType(String type);
	
	public Timestamp getCreated();

	public void setCreated(Timestamp created);

	public String getDescription();

	public void setDescription(String description);

	public UserProxy getUser();

	public void setUser(UserProxy user);
}
