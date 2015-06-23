package de.rtcustomz.getraenkeautomat.client.admin;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.rtcustomz.getraenkeautomat.client.Page;

public abstract class AdminPage extends Composite implements Page, HasWidgets {
	public abstract void showGrid();
	


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
