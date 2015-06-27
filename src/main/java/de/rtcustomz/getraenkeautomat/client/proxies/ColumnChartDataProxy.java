package de.rtcustomz.getraenkeautomat.client.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

import de.rtcustomz.getraenkeautomat.server.entities.ColumnChartData;

@ProxyFor(ColumnChartData.class)
public interface ColumnChartDataProxy extends ValueProxy {
	public Integer getX();
	
	public Integer getY();
	
	public String getDrink();
}
