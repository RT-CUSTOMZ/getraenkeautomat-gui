package de.rtcustomz.getraenkeautomat.client.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

import de.rtcustomz.getraenkeautomat.server.entities.PieChartData;

@ProxyFor(PieChartData.class)
public interface PieChartDataProxy extends ValueProxy {
	public String getDrink();
	
	public long getCount();
	
//	public void setDrink(String drink);
//	
//	public void setCount(Integer count);
}
