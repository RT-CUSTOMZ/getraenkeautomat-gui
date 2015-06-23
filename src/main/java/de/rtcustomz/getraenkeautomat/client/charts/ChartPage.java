package de.rtcustomz.getraenkeautomat.client.charts;

import java.util.Iterator;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

import de.rtcustomz.getraenkeautomat.client.Page;

public abstract class ChartPage extends ResizeComposite implements Page, HasWidgets {
	abstract void drawChart();
	


	@Override
	public void add(Widget w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}
}
