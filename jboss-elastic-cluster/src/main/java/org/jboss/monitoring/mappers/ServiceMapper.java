package org.jboss.monitoring.mappers;

public class ServiceMapper implements MappedServiceSet{

	@Override
	public synchronized void setService(String jndiName, String serviceName,
			int minThreshold, int warnThreshold, int criticalThreshold) {
		
	}

	@Override
	public synchronized Object getService(String jndiName, String serviceName) {
		
		return null;
	}

}
