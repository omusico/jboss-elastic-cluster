//package org.jboss.monitoring.GridControl.managers;
//
//import java.io.IOException;
//import java.util.Set;
//
//import javax.management.InstanceNotFoundException;
//import javax.management.MalformedObjectNameException;
//import javax.naming.NamingException;
//
//import org.infinispan.Cache;
//import org.infinispan.manager.DefaultCacheManager;
//import org.infinispan.manager.EmbeddedCacheManager;
//import org.jboss.monitoring.mappers.JMXServiceMapper;
//
///**
// * A simple mapper for Infinispan using MBean resolution
// * @author cvarga
// *
// */
//public class InfinispanJMXManager implements DistributedCacheManager {
//
//	private final String 			_jmxObjectString;
//	
//	private Cache<String,Object> 	_cache;
//	
//	
//	public InfinispanJMXManager(final String jmxObjectPath)
//	{
//		this._jmxObjectString = jmxObjectPath;
//	}
//	
//	
//	@Override
//	public void allocCache() throws CacheManagerException {
//		
//		try {
//			EmbeddedCacheManager advancedCache = JMXServiceMapper.getJMXObject(EmbeddedCacheManager.class,_jmxObjectString);
//			
//			_cache = advancedCache.getCache();
//			//_cache = (Cache)advancedCache;
//			
//		} catch (MalformedObjectNameException e) {
//			throw new CacheManagerException("Cannot alocate JMX cache  ", e);
//		} catch (InstanceNotFoundException e) {
//			throw new CacheManagerException("Cannot alocate JMX cache  ", e);
//		} catch (NullPointerException e) {
//			throw new CacheManagerException("Cannot alocate JMX cache  ", e);
//		} catch (NamingException e) {
//			throw new CacheManagerException("Cannot alocate JMX cache  ", e);
//		} catch (IOException e) {
//			throw new CacheManagerException("Cannot alocate JMX cache  ", e);
//		}
//
//	}
//	
//	public void setCache(String cacheName)
//	{
//		
//	}
//	
//	
//	public Set<String> getCacheNames() throws CacheManagerException
//	{
//		//TODO Retrieve cache entries using JMX
//		return null;
//	}
//
//	@Override
//	public Object getKeyValue(String key) {
//		return _cache.get(key);
//	}
//
//	@Override
//	public void setKeyValue(String key, Object value) {
//		_cache.put(key,value);
//	}
//
//}
