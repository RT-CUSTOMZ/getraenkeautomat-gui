package de.rtcustomz.getraenkeautomat.shared;

import java.util.Date;

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
	
	public Date getCreated();

	public void setCreated(Date created);

	public String getDescription();

	public void setDescription(String description);

	public UserProxy getUser();

	public void setUser(UserProxy user);
}
