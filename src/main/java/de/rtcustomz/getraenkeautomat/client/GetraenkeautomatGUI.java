package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class GetraenkeautomatGUI implements EntryPoint {
	// private final Messages messages = GWT.create(Messages.class);

	// private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);

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

		wrapper.getElement().setId("wrapper");
    	wrapper.add(header);
    	wrapper.add(nav);
    	wrapper.add(clear);
    	wrapper.add(footer);
    	
    	RootLayoutPanel.get().add(wrapper);
		
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
}
