package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.Slot;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class SlotDAO {
//	private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countSlots() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM Slot", Long.class);
		Long count = q.getSingleResult();
		em.close();
		return count;
	}
	
	public static List<Slot> findAllSlots() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Slot> q = em.createQuery("FROM Slot", Slot.class);
		List<Slot> allSlots = q.getResultList();
		em.close();
		return allSlots;
	}
	
	public static List<Slot> findSlotEntries(int firstResult, int maxResults) {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Slot> q = em.createQuery("FROM Slot", Slot.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		List<Slot> slots = q.getResultList();
		em.close();
		return slots;
	}
	
	public static Slot findById(Integer id) {
		EntityManager em = DatabaseController.createEntityManager();
		Slot slot = em.find(Slot.class, id);
		em.close();
		return slot;
	}
	
	public static void save(Slot slot) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		} finally {
			em.close();
		}
	}
	
	public static Slot createSlot(Integer id, String drink) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		} finally {
			em.close();
		}
	}

	public static void delete(Slot slot) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
		try {
			em.getTransaction().begin();
			slot=em.find(Slot.class, slot.getId());
			em.remove(slot);
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
