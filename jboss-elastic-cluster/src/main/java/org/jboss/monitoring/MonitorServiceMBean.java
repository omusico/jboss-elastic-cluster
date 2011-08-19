package org.jboss.monitoring;

import javax.management.Notification;


public interface MonitorServiceMBean {

	public abstract void setAdapterName(String adapterName);

	public abstract void setServerURL(String serverURL);
	
	public abstract String getServerURL();
	
	public abstract String getAdapterName();
	
	public abstract void execute();

	public void create() throws Exception;
	
	public void destroy() throws Exception;

	
}