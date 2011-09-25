package org.jboss.monitoring.GridControl.managers;



public interface DistributedCacheManager {
	
	public void allocCache() throws CacheManagerException; 
	
	public Object getKeyValue(String key);
	public void   setKeyValue(String key, Object value);
}
