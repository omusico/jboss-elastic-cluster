package org.jboss.monitoring.mappers;


public interface MappedServiceSet {
	
	public void  setService(String jndiName,String serviceName ,int minThreshold, int warnThreshold, int criticalThreshold);
	public Object getService(String jndiName,String serviceName);

}
