package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.user.client.ui.ScrollPanel;

public interface Page {
//	public FlowPanel page = new FlowPanel();
	public ScrollPanel page = new ScrollPanel();
	public abstract void initPage();
}
