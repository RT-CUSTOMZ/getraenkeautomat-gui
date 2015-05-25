package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.Slot;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class SlotDAO {
	private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countSlots() {
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM slots;", Long.class);
		return q.getSingleResult();
	}
	
	public static List<Slot> findAllSlots() {
		TypedQuery<Slot> q = em.createQuery("FROM slots;", Slot.class);
		return q.getResultList();
	}
	
	public static List<Slot> findSlotEntries(int firstResult, int maxResults) {
		TypedQuery<Slot> q = em.createQuery("FROM slots;", Slot.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}
	
	public static Slot findById(Integer id) {
		return em.find(Slot.class, id);
	}
	
	public static void save(Slot slot) throws Exception {
		try {
			em.getTransaction().begin();
			em.persist(slot);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}
	
	public static Slot createSlot(Integer id, String drink) throws Exception {
		try {
			em.getTransaction().begin();
			Slot slot = new Slot(id, drink);
			em.persist(slot);
			em.flush();
			em.getTransaction().commit();
			
			return slot;
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}

	public static void delete(Slot slot) throws Exception {
		try {
			em.getTransaction().begin();
			em.remove(slot);
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
