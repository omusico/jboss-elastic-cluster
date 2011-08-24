/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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
