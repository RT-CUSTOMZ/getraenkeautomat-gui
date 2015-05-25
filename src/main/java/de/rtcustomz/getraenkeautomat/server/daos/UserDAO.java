package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.User;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class UserDAO {
	private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countUsers() {
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM users;", Long.class);
		return q.getSingleResult();
	}
	
	public static List<User> findAllUsers() {
		TypedQuery<User> q = em.createQuery("FROM users;", User.class);
		return q.getResultList();
	}
	
	public static List<User> findUserEntries(int firstResult, int maxResults) {
		TypedQuery<User> q = em.createQuery("FROM users;", User.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}
	
	public static User findById(int id) {
		return em.find(User.class, id);
	}
	
	public static void save(User user) throws Exception {
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}
	
	public static User createUser(String first_name, String last_name, String nickname) throws Exception {
		try {
			em.getTransaction().begin();
			User User = new User(first_name, last_name, nickname);
			em.persist(User);
			em.flush();
			em.getTransaction().commit();
			
			return User;
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception ignore) {}
			throw e;
		}
	}

	public static void delete(User user) throws Exception {
		try {
			em.getTransaction().begin();
			em.remove(user);
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
