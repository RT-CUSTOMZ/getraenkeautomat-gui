package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.Card;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class CardDAO {
	//private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countCards() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM Card", Long.class);
		Long count = q.getSingleResult();
		em.close();
		return count;
	}
	
	public static List<Card> findAllCards() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Card> q = em.createQuery("FROM Card", Card.class);
		List<Card> cards = q.getResultList();
		em.close();
		return cards;
	}
	
	public static List<Card> findCardEntries(int firstResult, int maxResults) {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Card> q = em.createQuery("FROM Card", Card.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		List<Card> cards = q.getResultList();
		em.close();
		return cards;
	}
	
	public static Card findById(String id) {
		EntityManager em = DatabaseController.createEntityManager();
		Card card = em.find(Card.class, id);
		em.close();
		return card;
	}
	
	public static void save(Card card) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		finally {
			em.close();
		}
	}
	
	public static Card createCard(String id, String type) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		finally {
			em.close();
		}
	}

	public static void delete(Card card) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
		try {
			em.getTransaction().begin();
			card=em.find(Card.class, card.getId());
			em.remove(card);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
		finally {
			em.close();
		}
	}
}
