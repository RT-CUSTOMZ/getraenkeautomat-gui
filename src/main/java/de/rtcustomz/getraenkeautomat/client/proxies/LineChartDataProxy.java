package de.rtcustomz.getraenkeautomat.client.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

import de.rtcustomz.getraenkeautomat.server.entities.LineChartData;

@ProxyFor(LineChartData.class)
public interface LineChartDataProxy extends ValueProxy {
	public Integer getX();
	
	public Integer getY();
	
	public String getDrink();
}
