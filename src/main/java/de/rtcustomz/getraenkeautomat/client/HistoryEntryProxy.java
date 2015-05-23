package de.rtcustomz.getraenkeautomat.client;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import de.rtcustomz.getraenkeautomat.model.HistoryEntry;
import de.rtcustomz.getraenkeautomat.server.HistoryEntryLocator;

@ProxyFor(value = HistoryEntry.class, locator = HistoryEntryLocator.class)
public interface HistoryEntryProxy extends EntityProxy {
	public long getId();
	
	public CardProxy getCard();

	public Date getTime();

	public SlotProxy getSlot();

	public void setId(long id);

	public void setCard(CardProxy card);

	public void setTime(Date time);

	public void setSlot(SlotProxy slot);
}
