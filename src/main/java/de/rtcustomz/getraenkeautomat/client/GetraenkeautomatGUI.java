package de.rtcustomz.getraenkeautomat.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class GetraenkeautomatGUI implements EntryPoint {
//	private final char[] rtsigns = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '∮' , 'Ä', 'Ö', 'Ü', 'ß', '@' };
	private final char[] rtsigns = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ∮ÄÖÜß@".toCharArray();
	private final long startOfRT = 942930000;	// 18.11.1999 13:00

	// private final Messages messages = GWT.create(Messages.class);

	//private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MyResources.INSTANCE.css().ensureInjected();
		
		FlowPanel wrapper = new FlowPanel();
		FlowPanel clear = new FlowPanel();
		clear.setStyleName("clear");

		Header header = new Header();
		Navigation nav = new Navigation();
		Footer footer = new Footer();
		
//		FlowPanel wrapHeader = new FlowPanel();
//    	wrapHeader.add(header);
//    	
//    	FlowPanel wrapNav = new FlowPanel();
//    	wrapNav.add(nav);
    	
		wrapper.getElement().setId("wrapper");
    	wrapper.add(header);
    	wrapper.add(nav);
    	wrapper.add(clear);
    	wrapper.add(footer);
    	
    	RootLayoutPanel.get().add(wrapper);
    	
		Timer t = new Timer() {
			@Override
			public void run() {
				displayRT42();
			}
		};
		t.scheduleRepeating(1000);
		
//		final EventBus eventBus = new SimpleEventBus();
//		requestFactory.initialize(eventBus);

//		CardRequest request = requestFactory.cardRequest();
//		CardProxy newCard = request.create(CardProxy.class);
//
//		newCard.setId("42");
//		newCard.setType("42");
//		newCard.setDescription("test card created by GWT");
//		newCard.setCreated(new Date());
//		
//		request.save(newCard).fire(new Receiver<Void>() {
//			@Override
//			public void onSuccess(Void arg0) {
//				dialogBox.setText("Remote Procedure Call");
//				serverResponseLabel
//						.removeStyleName("serverResponseLabelError");
//				serverResponseLabel
//						.setHTML("new card created, check your database");
//				dialogBox.center();
//				closeButton.setFocus(true);
//			}
//
//			@Override
//			public void onFailure(ServerFailure error) {
//				// Show the RPC error message to the user
//				 dialogBox.setText("Remote Procedure Call - Failure");
//				 serverResponseLabel.addStyleName("serverResponseLabelError");
//				 serverResponseLabel.setHTML(error.getMessage());
//				 dialogBox.center();
//				 closeButton.setFocus(true);
//			}
//		});
	}

	protected void displayRT42() {
		Date now = new Date();
		long RT42_timestamp = now.getTime()/1000 - startOfRT;
		
		String rt42time = getRT42String(RT42_timestamp);
		Document.get().getElementById("RTZEIT").setInnerHTML(rt42time);
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
}
