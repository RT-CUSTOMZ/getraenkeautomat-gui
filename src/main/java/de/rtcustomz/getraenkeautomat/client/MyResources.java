package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;

public interface MyResources extends ClientBundle {
	public static final MyResources INSTANCE = GWT.create(MyResources.class);

	@NotStrict
	@Source("default.css")
	public CssResource css();

//	@Source("applicationBackground.png")
//	DataResource applicationBackground();

	@Source("header_logo.png")
	ImageResource logo();
}
