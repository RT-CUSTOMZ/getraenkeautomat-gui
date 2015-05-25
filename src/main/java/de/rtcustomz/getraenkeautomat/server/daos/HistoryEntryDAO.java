package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.Card;
import de.rtcustomz.getraenkeautomat.server.entities.HistoryEntry;
import de.rtcustomz.getraenkeautomat.server.entities.Slot;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class HistoryEntryDAO {
	private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countHistoryEntries() {
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM history;", Long.class);
		return q.getSingleResult();
	}
	
	public static List<HistoryEntry> findAllHistoryEntries() {
		TypedQuery<HistoryEntry> q = em.createQuery("FROM history;", HistoryEntry.class);
		return q.getResultList();
	}
	
	public static List<HistoryEntry> findHistoryEntries(int firstResult, int maxResults) {
		TypedQuery<HistoryEntry> q = em.createQuery("FROM history;", HistoryEntry.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}
	
	public static HistoryEntry findById(Long id) {
		return em.find(HistoryEntry.class, id);
	}
	
	public static void save(HistoryEntry historyEntry) throws Exception {
		try {
			em.getTransaction().begin();
			em.persist(historyEntry);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}
	
	public static HistoryEntry createHistoryEntry(Card card, Slot slot) throws Exception {
		try {
			em.getTransaction().begin();
			HistoryEntry historyEntry = new HistoryEntry(card, slot);
			em.persist(historyEntry);
			em.flush();
			em.getTransaction().commit();
			
			return historyEntry;
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}

	public static void delete(HistoryEntry historyEntry) throws Exception {
		try {
			em.getTransaction().begin();
			em.remove(historyEntry);
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
