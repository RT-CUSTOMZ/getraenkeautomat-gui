package de.rtcustomz.getraenkeautomat.server;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.rtcustomz.getraenkeautomat.client.CardService;
import de.rtcustomz.getraenkeautomat.model.Card;
import de.rtcustomz.getraenkeautomat.model.User;
import de.rtcustomz.getraenkeautomat.util.DatabaseController;

public class CardServiceImpl extends RemoteServiceServlet implements CardService {

	private static final long serialVersionUID = 5632382765116452677L;
	
	@Resource
	private UserTransaction utx;
	
	private EntityManager em;
	
	@PostConstruct
	public void init() {
		em = DatabaseController.createEntityManager();
	}
	
	@PreDestroy
	public void destroy() {
		em.close();
	}

	@Override
	public Card getCard(String card_id) {
		return em.find(Card.class, card_id);
	}

	@Override
	public void changeDescription(Card card, String description) throws Exception {
		try {
//			if(em.find(Card.class, card.getId()) != null)
//				throw IrgendeineException("alles kaputt!!!");
//			else {
				utx.begin();
				em.joinTransaction();
				card.setDescription(description);
				em.persist(card);
				em.flush();
				utx.commit();
//			}
		} finally {
			try {
				utx.rollback();
			} catch (Exception ignore) {}
		}
	}

	@Override
	public void changeUser(Card card, User user) throws Exception {
		try {
//			if(em.find(Card.class, card.getId()) != null)
//				throw IrgendeineException("alles kaputt!!!");
//			else {
				utx.begin();
				em.joinTransaction();
				card.setUser(user);
				em.persist(card);
				em.flush();
				utx.commit();
//			}
		} finally {
			try {
				utx.rollback();
			} catch (Exception ignore) {}
		}
	}

	@Override
	public void deleteCard(Card card) throws Exception {
		try {
			utx.begin();
			em.joinTransaction();
			em.remove(card);
			em.flush();
			utx.commit();
		} finally {
			try {
				utx.rollback();
			} catch (Exception ignore) {}
		}
	}

	@Override
	public List<Card> getCards() {
		// TODO Auto-generated method stub
		return null;
	}
}
