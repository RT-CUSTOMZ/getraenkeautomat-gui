package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.Card;
import de.rtcustomz.getraenkeautomat.server.entities.HistoryEntry;
import de.rtcustomz.getraenkeautomat.server.entities.PieChartData;
import de.rtcustomz.getraenkeautomat.server.entities.Slot;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class HistoryEntryDAO {
	private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countHistoryEntries() {
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM HistoryEntry", Long.class);
		return q.getSingleResult();
	}
	
	public static List<HistoryEntry> findAllHistoryEntries() {
		TypedQuery<HistoryEntry> q = em.createQuery("FROM HistoryEntry", HistoryEntry.class);
		return q.getResultList();
	}
	
	public static List<HistoryEntry> findHistoryEntries(int firstResult, int maxResults) {
		TypedQuery<HistoryEntry> q = em.createQuery("FROM HistoryEntry", HistoryEntry.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}
	
	public static List<PieChartData> getPieChartData(int month, int year) {
		final String queryString;
		
		queryString = "SELECT new de.rtcustomz.getraenkeautomat.server.entities.PieChartData(s.drink AS drink, count(*) AS count) "
					+ "FROM HistoryEntry h JOIN h.slot s "
					+ "WHERE month(h.time) = :month AND year(h.time) = :year "
					+ "GROUP BY s.drink";
		
		TypedQuery<PieChartData> q = em.createQuery(queryString, PieChartData.class);
		
		q.setParameter("month", month);
		q.setParameter("year", year);
		
		final List<PieChartData> resultList = q.getResultList();
		
		return resultList;
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
			historyEntry=em.find(HistoryEntry.class, historyEntry.getId());
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
