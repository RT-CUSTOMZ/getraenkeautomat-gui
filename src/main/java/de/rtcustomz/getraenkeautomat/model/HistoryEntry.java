package de.rtcustomz.getraenkeautomat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="history")
public class HistoryEntry {
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private int version;
	
	@ManyToOne
	@JoinColumn(name="card_id", referencedColumnName="id")
	private Card card;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="htime", nullable=false, updatable=false)
	private Date time;
	
	@ManyToOne
	@JoinColumn(name="slot_id", referencedColumnName="id", nullable=false, updatable=false)
	private Slot slot;
	
	public HistoryEntry() {}
	
	public HistoryEntry(Card card, Slot slot) {
		this.card = card;
		this.time = new Date();
		this.slot = slot;
	}

	public long getId() {
		return id;
	}
	
	public Card getCard() {
		return card;
	}

	public Date getTime() {
		return time;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
