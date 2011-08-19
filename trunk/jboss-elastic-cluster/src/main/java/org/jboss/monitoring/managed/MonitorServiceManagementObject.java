package org.jboss.monitoring.managed;

import java.util.Set;

import org.jboss.managed.api.annotation.ManagementComponent;
import org.jboss.managed.api.annotation.ManagementObject;
import org.jboss.managed.api.annotation.ManagementProperties;
import org.jboss.monitoring.MonitorService;
import org.jboss.monitoring.MonitorServiceSet;

@ManagementObject(name="MonitorService", 
				  componentType=@ManagementComponent(type="MCBean", subtype="MonitorService"), 
				  properties=ManagementProperties.EXPLICIT, 
				  description="MonitorService enables controlled management of JBoss instances in cluster.")
public class MonitorServiceManagementObject {
	
	private final MonitorService monitorService;
	
	public MonitorServiceManagementObject(String partitionName, Set<MonitorServiceSet> setList) {
		this.monitorService = new MonitorService(partitionName);
	}
	
	public MonitorService getMonitorService() {
		return this.monitorService;
	}

}
