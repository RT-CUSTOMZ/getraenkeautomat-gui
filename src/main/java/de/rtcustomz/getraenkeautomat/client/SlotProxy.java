package de.rtcustomz.getraenkeautomat.client;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import de.rtcustomz.getraenkeautomat.model.Slot;
import de.rtcustomz.getraenkeautomat.server.SlotLocator;

@ProxyFor(value = Slot.class, locator = SlotLocator.class)
public interface SlotProxy extends EntityProxy {
	public int getId();
	
	public String getDrink();

	public void setId(int id);

	public void setDrink(String drink);
}
