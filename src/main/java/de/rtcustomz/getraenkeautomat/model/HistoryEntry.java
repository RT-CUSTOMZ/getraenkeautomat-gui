package de.rtcustomz.getraenkeautomat.model;

import java.sql.Timestamp;
import java.util.Calendar;

public class HistoryEntry {
	private String card_id;
	private final Timestamp time;
	private final Integer slot;
	
	
	public HistoryEntry(String card_id, Integer slot) {
		this.card_id = card_id;
		this.time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		this.slot = slot;
	}


	public String getCard_id() {
		return card_id;
	}


	public Timestamp getTime() {
		return time;
	}


	public Integer getSlot() {
		return slot;
	}

}
