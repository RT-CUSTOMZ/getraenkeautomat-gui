package de.rtcustomz.getraenkeautomat.server;

import com.google.web.bindery.requestfactory.shared.Locator;

import de.rtcustomz.getraenkeautomat.model.Slot;

public class SlotLocator extends Locator<Slot, Integer> {

	@Override
	public Slot create(Class<? extends Slot> clazz) {
		return new Slot();
	}

	@Override
	public Slot find(Class<? extends Slot> clazz, Integer id) {
		return SlotDAO.findById(id);
	}

	@Override
	public Class<Slot> getDomainType() {
		return Slot.class;
	}

	@Override
	public Integer getId(Slot domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Integer> getIdType() {
		return Integer.class;
	}

	@Override
	public Object getVersion(Slot domainObject) {
		return domainObject.getVersion();
	}
}
