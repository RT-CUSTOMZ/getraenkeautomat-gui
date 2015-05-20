package de.rtcustomz.getraenkeautomat.model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.google.web.bindery.requestfactory.shared.Locator;

import de.rtcustomz.getraenkeautomat.util.DatabaseController;

@Singleton
public class CardLocator extends Locator<Card, String> {
	
	@Resource
	UserTransaction utx;
	
	EntityManager em;
	
	@PostConstruct
	public void init() {
		em = DatabaseController.createEntityManager();
	}
	
	@PreDestroy
	public void destroy() {
		em.close();
	}

	@Override
	public Card create(Class<? extends Card> clazz) {
		return new Card();
	}

	@Override
	public Card find(Class<? extends Card> clazz, String id) {
		return em.find(Card.class, id);
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
