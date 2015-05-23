package de.rtcustomz.getraenkeautomat.server;

import com.google.web.bindery.requestfactory.shared.Locator;

import de.rtcustomz.getraenkeautomat.model.HistoryEntry;

public class HistoryEntryLocator extends Locator<HistoryEntry, Long> {

	@Override
	public HistoryEntry create(Class<? extends HistoryEntry> clazz) {
		return new HistoryEntry();
	}

	@Override
	public HistoryEntry find(Class<? extends HistoryEntry> clazz, Long id) {
		return HistoryEntryDAO.findById(id);
	}

	@Override
	public Class<HistoryEntry> getDomainType() {
		return HistoryEntry.class;
	}

	@Override
	public Long getId(HistoryEntry domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Object getVersion(HistoryEntry domainObject) {
		return domainObject.getVersion();
	}
}
