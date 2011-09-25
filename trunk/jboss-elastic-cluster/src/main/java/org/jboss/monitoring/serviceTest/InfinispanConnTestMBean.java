package org.jboss.monitoring.serviceTest;

import org.jboss.ejb3.annotation.Management;
import org.jboss.ejb3.annotation.Service;
import org.jboss.logging.Logger;
import org.jboss.monitoring.GridControl.managers.CacheManagerException;
import org.jboss.monitoring.GridControl.managers.DistributedCacheManager;
import org.jboss.monitoring.GridControl.managers.InfinispanJNDIManager;

@Service (objectName="jboss.elasticmon:service=TestService")
@Management (InfinispanConnTest.class)
public class InfinispanConnTestMBean implements InfinispanConnTest {
	
	private static final Logger log = Logger.getLogger(InfinispanConnTestMBean.class.getName());
	String service;
	
	DistributedCacheManager cacheJNDI; 
	DistributedCacheManager cacheJMX; 
	
	// Lifecycle methods
	public void create() throws Exception {
		this.service = "";
		log.info("ElasticMonitorMBean - Creating");

		//MBeanServerConnection adaptor = JNDIServiceMapper.JNDIServiceMapper(MBeanServerConnection.class, "127.0.0.1", "jmx/invoker/RMIAdaptor");
		//String objectName = "org.infinispan:component=Cache,manager=\"ha-partition\",name=\"distributed-state(repl_sync)\",type=Cache";
		
		cacheJNDI = getJNDIInfinispan();
		//cacheJMX = getJMXInfinispan();
		
		return;
	}
	 
	public void destroy() {
		log.info("ElasticMonitorMBean - Destroying");
	}

	@Override
	public void setJNDIDistributedKV(String key,String value) {
		cacheJNDI.setKeyValue(key, value);
	}

	@Override
	public Object getJNDIDistributedKV(String key) {
		Object objValue = cacheJNDI.getKeyValue(key);
		if(objValue == null || !(objValue instanceof String))
			return "NULL";
		
		return objValue.toString();
	}
	
	@Override
	public void setJMXDistributedKV(String key,String value) {
		cacheJMX.setKeyValue(key, value);
	}

	@Override
	public Object getJMXDistributedKV(String key) {
		return cacheJMX.getKeyValue(key);
	}
	
	private DistributedCacheManager getJNDIInfinispan() throws Exception
	{
		DistributedCacheManager manager = new InfinispanJNDIManager("127.0.0.1", "java:CacheManager/ha-partition","distributed-state");
	
		try {
			
			manager.allocCache();
			return manager;
			
		} catch (CacheManagerException e) {
			throw new Exception(e);
		}
	}
	
//	private DistributedCacheManager getJMXInfinispan() throws Exception
//	{
//		DistributedCacheManager jmxManager = new InfinispanJMXManager("org.infinispan:component=CacheManager,name=\"ha-partition\",type=CacheManager");
//		try {
//			
//			jmxManager.allocCache();
//			return jmxManager;
//			
//		} catch (CacheManagerException e) {
//			throw new Exception(e);
//		}
//	}
}
