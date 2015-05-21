package de.rtcustomz.getraenkeautomat.server;

import java.util.ArrayList;
import java.util.List;

import de.rtcustomz.getraenkeautomat.model.Card;

public class CardDAO {
	public static Long countCards() {
		return 42l;
	}
	
	public static List<Card> findAllCards() {
		return new ArrayList<Card>();
	}
	
	public static List<Card> findCardEntries(int firstResult, int maxResults) {
		return new ArrayList<Card>();
	}
	
	public static Card findCard(int id) {
		return new Card();
	}
	
	public static void save(Card card) {
		
	}
	
	public static Card createCard(String id, String type) {
		return new Card();
	}

	public static void delete(Card card) {
		
	}
}
