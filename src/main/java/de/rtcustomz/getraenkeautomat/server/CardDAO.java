package de.rtcustomz.getraenkeautomat.server;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.model.Card;
import de.rtcustomz.getraenkeautomat.util.DatabaseController;

@Singleton
public class CardDAO {
	private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countCards() {
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM cards;", Long.class);
		return q.getSingleResult();
	}
	
	public static List<Card> findAllCards() {
		TypedQuery<Card> q = em.createQuery("FROM cards;", Card.class);
		return q.getResultList();
	}
	
	public static List<Card> findCardEntries(int firstResult, int maxResults) {
		TypedQuery<Card> q = em.createQuery("FROM cards;", Card.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}
	
	public static Card findById(String id) {
		return em.find(Card.class, id);
	}
	
	public static void save(Card card) throws Exception {
		try {
			em.getTransaction().begin();
			em.persist(card);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}
	
	public static Card createCard(String id, String type) throws Exception {
		try {
			em.getTransaction().begin();
			Card card = new Card(id, type);
			em.persist(card);
			em.flush();
			em.getTransaction().commit();
			
			return card;
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}

	public static void delete(Card card) throws Exception {
		try {
			em.getTransaction().begin();
			em.remove(card);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}
}
