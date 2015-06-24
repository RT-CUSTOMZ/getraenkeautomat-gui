package de.rtcustomz.getraenkeautomat.client.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import de.rtcustomz.getraenkeautomat.client.Page;

public abstract class AdminPage extends Composite implements Page {
	public FlowPanel page = new FlowPanel();

	public abstract void showGrid();
}
