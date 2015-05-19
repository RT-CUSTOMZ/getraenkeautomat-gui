package de.rtcustomz.getraenkeautomat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.rtcustomz.getraenkeautomat.model.Card;
import de.rtcustomz.getraenkeautomat.model.User;

@RemoteServiceRelativePath("cards")
public interface CardService extends RemoteService {
	Card getCard(String card_id);
	List<Card> getCards();
	void changeDescription(Card card, String description) throws Exception;
	void changeUser(Card card, User user) throws Exception;
	void deleteCard(Card card) throws Exception;
}
