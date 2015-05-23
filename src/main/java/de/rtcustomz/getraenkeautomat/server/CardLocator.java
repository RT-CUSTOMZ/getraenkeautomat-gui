package de.rtcustomz.getraenkeautomat.server;

import com.google.web.bindery.requestfactory.shared.Locator;

import de.rtcustomz.getraenkeautomat.model.Card;

public class CardLocator extends Locator<Card, String> {

	@Override
	public Card create(Class<? extends Card> clazz) {
		return new Card();
	}

	@Override
	public Card find(Class<? extends Card> clazz, String id) {
		return CardDAO.findById(id);
	}

	@Override
	public Class<Card> getDomainType() {
		return Card.class;
	}

	@Override
	public String getId(Card domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(Card domainObject) {
		return domainObject.getVersion();
	}
}
