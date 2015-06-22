package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public abstract class Page extends Composite {
	FlowPanel page = new FlowPanel();
	public abstract void initPage();
}
