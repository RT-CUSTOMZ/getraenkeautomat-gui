package de.rtcustomz.getraenkeautomat.client;

import java.util.Date;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class RTClock extends Timer implements IsWidget { 
//	private final char[] rtsigns = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '∮' , 'Ä', 'Ö', 'Ü', 'ß', '@' };
	private final char[] rtsigns = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ∮ÄÖÜß@".toCharArray();
	private final long startOfRT = 942930000;	// 18.11.1999 13:00
	private Widget rtzeit;
	
	public RTClock(String id) {
		rtzeit = new Anchor("RT Zeit", "http://42volt.de/?page_id=34");
		rtzeit.getElement().setId(id);
	}
	
	@Override
	public void run() {
		displayRT42();
	}
	
	protected void displayRT42() {
		Date now = new Date();
		long RT42_timestamp = now.getTime()/1000 - startOfRT;
		
		String rt42time = getRT42String(RT42_timestamp);
		Document.get().getElementById("RTZEIT").setInnerHTML(rt42time);
		//rtzeit.setIn
	}

	private String getRT42String(long rT42_timestamp) {
		String rt42time = "";
		
		int anzahl = anz_RT42_stellen(rT42_timestamp);
		for(int i = anzahl-1; i >= 0; i--)
		{
			int stelle = (int) (rT42_timestamp/Math.pow(42,i));
			rt42time += rtsigns[stelle];
			rT42_timestamp = rT42_timestamp % (long)Math.pow(42,i);
		}
		
		return rt42time;
	}
	
	private int anz_RT42_stellen(long rT42_timestamp)
	{
		int erg = 0;
		while( Math.pow(42,erg) <= (rT42_timestamp+1) )
			erg++;
		return erg;
	}

	@Override
	public Widget asWidget() {
		return rtzeit;
	}
}
