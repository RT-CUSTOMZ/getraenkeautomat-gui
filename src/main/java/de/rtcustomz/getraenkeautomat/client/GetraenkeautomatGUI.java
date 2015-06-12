package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;

public class GetraenkeautomatGUI implements EntryPoint {
	// private final Messages messages = GWT.create(Messages.class);

	// private final ModelRequestFactory requestFactory = GWT.create(ModelRequestFactory.class);
	
	private SimpleLayoutPanel layoutPanel;
    private PieChart pieChart;

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
    	
    	FlowPanel content = new FlowPanel();
    	content.getElement().setId("content");
    	
    	
    	
    	content.add(getSimpleLayoutPanel());

        // Create the API Loader
        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {

            @Override
            public void run() {
                getSimpleLayoutPanel().setWidget(getPieChart());
                drawPieChart();
            }
        });

    	
    	
    	
    	// TODO: add content to contentFlowPanel, e.g.:
    	// content.add(new HTMLPanel(HeadingElement.TAG_H1, "Hier kommt der Content rein!!!"));
    	
    	wrapper.add(content);
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
	
	private SimpleLayoutPanel getSimpleLayoutPanel() {
        if (layoutPanel == null) {
            layoutPanel = new SimpleLayoutPanel();
        }
        return layoutPanel;
	}
	
	private Widget getPieChart() {
		if (pieChart == null) {
	        pieChart = new PieChart();
		}
		return pieChart;
	}
	
	private void drawPieChart() {
		// Prepare the data
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Name");
		dataTable.addColumn(ColumnType.NUMBER, "Donuts eaten");
		dataTable.addRows(4);
		dataTable.setValue(0, 0, "Michael");
		dataTable.setValue(1, 0, "Elisa");
		dataTable.setValue(2, 0, "Robert");
		dataTable.setValue(3, 0, "John");
		dataTable.setValue(0, 1, 5);
		dataTable.setValue(1, 1, 7);
		dataTable.setValue(2, 1, 3);
		dataTable.setValue(3, 1, 2);
		
		// Draw the chart
		pieChart.draw(dataTable);
	}

}
