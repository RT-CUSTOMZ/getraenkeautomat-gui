package de.rtcustomz.getraenkeautomat.client.proxies;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import de.rtcustomz.getraenkeautomat.server.entities.Slot;
import de.rtcustomz.getraenkeautomat.server.locators.SlotLocator;

@ProxyFor(value = Slot.class, locator = SlotLocator.class)
public interface SlotProxy extends EntityProxy {
	public int getId();
	
	public String getDrink();

	public void setId(int id);

	public void setDrink(String drink);
}
