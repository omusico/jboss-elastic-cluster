package org.jboss.monitoring;

public class MonitorServiceSet {

	private String serviceName;
	
	private String limit;
	
	public MonitorServiceSet(String serviceName, String limit) {
		this.serviceName = serviceName;
		this.limit = limit;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
}
