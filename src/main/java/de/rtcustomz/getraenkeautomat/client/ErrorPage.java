package de.rtcustomz.getraenkeautomat.client;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ErrorPage extends Composite implements Page {

    static private ErrorPage _instance = null;
    private static final String pageName = "Fehler";

    public ErrorPage() {
	initPage();
	initWidget(page);
    }

    public static ErrorPage getInstance() {
	if (null == _instance) {
	    _instance = new ErrorPage();
	}
	return _instance;
    }

    public static String getPageName() {
	return pageName;
    }

    @Override
    public void initPage() {
	page.add(new HTMLPanel(HeadingElement.TAG_H1, "Fehler! Seite " + History.getToken()
		+ " konnte nicht gefunden werden!"));
    }

}
