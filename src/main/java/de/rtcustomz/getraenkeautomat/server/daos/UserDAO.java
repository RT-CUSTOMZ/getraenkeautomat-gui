package de.rtcustomz.getraenkeautomat.server.daos;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.rtcustomz.getraenkeautomat.server.entities.User;
import de.rtcustomz.getraenkeautomat.server.util.DatabaseController;

@Singleton
public class UserDAO {
	//private static EntityManager em = DatabaseController.createEntityManager();
	
	public static Long countUsers() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(*) FROM User", Long.class);
		Long count = q.getSingleResult();
		em.close();
		return count;
	}
	
	public static List<User> findAllUsers() {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<User> q = em.createQuery("FROM User", User.class);
		List<User> users = q.getResultList();
		em.close();
		return users;
	}
	
	public static List<User> findUserEntries(int firstResult, int maxResults) {
		EntityManager em = DatabaseController.createEntityManager();
		TypedQuery<User> q = em.createQuery("FROM User", User.class);
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		List<User> users = q.getResultList();
		em.close();
		return users;
	}
	
	public static User findById(int id) {
		EntityManager em = DatabaseController.createEntityManager();
		User user = em.find(User.class, id);
		em.close();
		return user;
	}
	
	public static void save(User user) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(user);
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
	
	public static User createUser(String first_name, String last_name, String nickname) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
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
		finally {
			em.close();
		}
	}

	public static void delete(User user) throws Exception {
		EntityManager em = DatabaseController.createEntityManager();
		try {
			em.getTransaction().begin();
			user=em.find(User.class, user.getId());
			em.remove(user);
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
