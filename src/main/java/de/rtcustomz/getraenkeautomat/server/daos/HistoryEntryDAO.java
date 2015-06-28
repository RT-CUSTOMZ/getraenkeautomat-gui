package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.Card;
import de.rtcustomz.getraenkeautomat.server.entities.ColumnChartData;
import de.rtcustomz.getraenkeautomat.server.entities.HistoryEntry;
import de.rtcustomz.getraenkeautomat.server.entities.LineChartData;
import de.rtcustomz.getraenkeautomat.server.entities.PieChartData;
import de.rtcustomz.getraenkeautomat.server.entities.Slot;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class HistoryEntryDAO {
//	private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countHistoryEntries() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM HistoryEntry", Long.class);
		Long count = q.getSingleResult();
		em.close();
		return count;
	}
	
	public static List<HistoryEntry> findAllHistoryEntries() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<HistoryEntry> q = em.createQuery("FROM HistoryEntry", HistoryEntry.class);
		List<HistoryEntry> history = q.getResultList();
		em.close();
		return history;
	}
	
	public static List<HistoryEntry> findHistoryEntries(int firstResult, int maxResults) {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<HistoryEntry> q = em.createQuery("FROM HistoryEntry", HistoryEntry.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		List<HistoryEntry> entries = q.getResultList();
		em.close();
		return entries;
	}
	
	public static List<LineChartData> getLineChartData(int month, int year) {
		EntityManager em = DatabaseController.createEntityManager();
		final String queryString;
		
		queryString = "SELECT new de.rtcustomz.getraenkeautomat.server.entities.LineChartData(day(h.time) AS timeSpan, count(*) AS count, s.drink AS drink) "
					+ "FROM HistoryEntry h JOIN h.slot s "
					+ "WHERE month(h.time) = :month AND year(h.time) = :year "
					+ "GROUP BY day(h.time), s.drink "
					+ "ORDER BY day(h.time) ASC";
		
		TypedQuery<LineChartData> q = em.createQuery(queryString, LineChartData.class);
		
		q.setParameter("month", month);
		q.setParameter("year", year);
		
		final List<LineChartData> resultList = q.getResultList();
		em.close();
		
		return resultList;
	}
	
	public static List<LineChartData> getLineChartData(int day, int month, int year) {
		EntityManager em = DatabaseController.createEntityManager();
		final String queryString;
		
		queryString = "SELECT new de.rtcustomz.getraenkeautomat.server.entities.LineChartData(hour(h.time) AS timeSpan, count(*) AS count, s.drink AS drink) "
					+ "FROM HistoryEntry h JOIN h.slot s "
					+ "WHERE day(h.time) = :day AND month(h.time) = :month AND year(h.time) = :year "
					+ "GROUP BY hour(h.time), s.drink "
					+ "ORDER BY hour(h.time) ASC";
		
		TypedQuery<LineChartData> q = em.createQuery(queryString, LineChartData.class);
		
		q.setParameter("day", day);
		q.setParameter("month", month);
		q.setParameter("year", year);
		
		final List<LineChartData> resultList = q.getResultList();
		em.close();
		
		return resultList;
	}
	
	public static List<ColumnChartData> getColumnChartData() {
		EntityManager em = DatabaseController.createEntityManager();
		final String queryString;
		
		queryString = "SELECT new de.rtcustomz.getraenkeautomat.server.entities.ColumnChartData(year(h.time) AS timeSpan, count(*) AS count, s.drink AS drink) "
					+ "FROM HistoryEntry h JOIN h.slot s "
					+ "GROUP BY year(h.time), s.drink "
					+ "ORDER BY year(h.time) ASC";
		
		TypedQuery<ColumnChartData> q = em.createQuery(queryString, ColumnChartData.class);
		
		final List<ColumnChartData> resultList = q.getResultList();
		em.close();
		
		return resultList;
	}
	
	public static List<ColumnChartData> getColumnChartData(int year) {
		EntityManager em = DatabaseController.createEntityManager();
		final String queryString;
		
		queryString = "SELECT new de.rtcustomz.getraenkeautomat.server.entities.ColumnChartData(month(h.time) AS timeSpan, count(*) AS count, s.drink AS drink) "
					+ "FROM HistoryEntry h JOIN h.slot s "
					+ "WHERE year(h.time) = :year "
					+ "GROUP BY month(h.time), s.drink "
					+ "ORDER BY month(h.time) ASC";
		
		TypedQuery<ColumnChartData> q = em.createQuery(queryString, ColumnChartData.class);
		
		q.setParameter("year", year);
		
		final List<ColumnChartData> resultList = q.getResultList();
		em.close();
		
		return resultList;
	}
	
	public static List<ColumnChartData> getColumnChartData(int week, int month, int year) {
		EntityManager em = DatabaseController.createEntityManager();
		final String queryString;
		
		queryString = "SELECT new de.rtcustomz.getraenkeautomat.server.entities.ColumnChartData(dayofweek(h.time) AS timeSpan, count(*) AS count, s.drink AS drink) "
					+ "FROM HistoryEntry h JOIN h.slot s "
					+ "WHERE year(h.time) = :year AND weekofyear(h.time) = :week " // AND month(h.time) = :month 
					+ "GROUP BY dayofweek(h.time), s.drink "
					+ "ORDER BY dayofweek(h.time) ASC";
		
		TypedQuery<ColumnChartData> q = em.createQuery(queryString, ColumnChartData.class);
		
		q.setParameter("week", week);
//		q.setParameter("month", month);
		q.setParameter("year", year);
		
		final List<ColumnChartData> resultList = q.getResultList();
		em.close();
		
		return resultList;
	}
	
	public static List<PieChartData> getPieChartData(int month, int year) {
		EntityManager em = DatabaseController.createEntityManager();
		final String queryString;
		
		queryString = "SELECT new de.rtcustomz.getraenkeautomat.server.entities.PieChartData(s.drink AS drink, count(*) AS count) "
					+ "FROM HistoryEntry h JOIN h.slot s "
					+ "WHERE month(h.time) = :month AND year(h.time) = :year "
					+ "GROUP BY s.drink";
		
		TypedQuery<PieChartData> q = em.createQuery(queryString, PieChartData.class);
		
		q.setParameter("month", month);
		q.setParameter("year", year);
		
		final List<PieChartData> resultList = q.getResultList();
		em.close();
		
		return resultList;
	}
	
	public static HistoryEntry findById(Long id) {
		EntityManager em = DatabaseController.createEntityManager();
		HistoryEntry singleEntry = em.find(HistoryEntry.class, id);
		em.close();
		return singleEntry;
	}
	
	public static void save(HistoryEntry historyEntry) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		} finally {
			em.close();
		}
	}
	
	public static HistoryEntry createHistoryEntry(Card card, Slot slot) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		} finally {
			em.close();
		}
	}

	public static void delete(HistoryEntry historyEntry) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		} finally {
			em.close();
		}
	}
}
