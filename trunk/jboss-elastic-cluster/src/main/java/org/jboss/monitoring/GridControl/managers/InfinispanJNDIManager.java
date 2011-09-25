package org.jboss.monitoring.GridControl.managers;

import java.util.Set;

import javax.naming.NamingException;

import org.infinispan.Cache;
import org.infinispan.CacheDelegate;
import org.jboss.ha.ispn.DefaultCacheContainer;
import org.jboss.monitoring.mappers.JNDIServiceMapper;

/**
 * A simple mapper for Infinispan using JNDI resolution
 * @author cvarga
 *
 */
public class InfinispanJNDIManager implements DistributedCacheManager {
	
	private Cache<String,Object> 	_cache;
	
	private String 					_contextURL;
	private final String 			_jndiName;
	private String 					_cacheName;
	
	
	/**
	 * Allocate instance infinispan cacheManager mapped by JNDI
	 * 
	 * contextUrl 	- usually the same URL/IP that is binded the app server.
	 * jndiName 	- the jndi where insinispan is mapped 
	 * 					Usually only the java:CacheManager/entity is exposed. Expose the ha-partition ! 
	 * 					HOWTO Ex: infinispan-configs.xml -  
	 * 					<infinispan-config name="ha-partition" jndi-name="java:CacheManager/ha-partition"> )
	 * cacheName - cache name that is available inside the partition. (Ex: distributed-state)
	 * 
	 * @param contextUrl
	 * @param jndiName
	 * @param cacheName
	 * @throws CacheManagerException 
	 */
	public InfinispanJNDIManager(final String contextUrl, final String jndiName, final String cacheName)
	{
		this._contextURL = contextUrl;
		this._jndiName = jndiName;
		this._cacheName = cacheName;
	}
	
	/**
	 * Allocate instance infinispan cacheManager mapped by JNDI
	 * 
	 * contextUrl 	- usually the same URL/IP that is binded the app server.
	 * jndiName 	- the jndi where insinispan is mapped 
	 * 					Usually only the java:CacheManager/entity is exposed. Expose the ha-partition ! 
	 * 					HOWTO Ex: infinispan-configs.xml -  
	 * 					<infinispan-config name="ha-partition" jndi-name="java:CacheManager/ha-partition"> )
	 * 
	 * @param contextUrl
	 * @param jndiName
	 */
	public InfinispanJNDIManager(final String contextUrl, final String jndiName)
	{
		this._contextURL = contextUrl;
		this._jndiName = jndiName;
	}
	
	/**
	 * Allocate instance infinispan cacheManager mapped by JNDI
	 * 
	 * contextUrl 	- usually the same URL/IP that is binded the app server.
	 * @param jndiName
	 */
	public InfinispanJNDIManager(final String jndiName)
	{
		this._jndiName = jndiName;
	}
	
	/**
	 * Allocate infinispan cache
	 * 
	 * @throws CacheManagerException
	 */
	public void allocCache() throws CacheManagerException
	{
		try {
			DefaultCacheContainer container = JNDIServiceMapper.JNDIServiceMapper(DefaultCacheContainer.class, _contextURL, _jndiName);
			
			Cache<String,Object> infinispanCache = getCache(container,_cacheName);
			
			this._cache = infinispanCache;
			
		} catch (NamingException e) {
			throw new CacheManagerException("Cannot alloc JNDI cache",e);
		}
		
	}
	
	private Cache<String,Object> getCache(DefaultCacheContainer defaultContainer, final String cacheName) throws CacheManagerException{
		
		Object objCache = defaultContainer.getCache(cacheName);
		if(objCache == null)
			throw new CacheManagerException("Cache not found !");
		
		if(!(objCache instanceof CacheDelegate))
			throw new CacheManagerException("Unable to parse Cache Delegate");
		
		@SuppressWarnings("unchecked")
		CacheDelegate<Object, Object> cacheDelegate = (CacheDelegate<Object, Object>)objCache;
		
		Cache<String,Object> cache =  cacheDelegate.getCacheManager().getCache(cacheName);
		return cache;
	}
	
	private DefaultCacheContainer getContainerByJNDI(final String contextUrl,final String jndiName) throws CacheManagerException{
		try{
			DefaultCacheContainer container = JNDIServiceMapper.JNDIServiceMapper(DefaultCacheContainer.class, contextUrl, jndiName);
			return container;	
		} 
		catch (NamingException e) {
			throw new CacheManagerException("Cannot alloc JNDI cache",e);
		}
	}
	
	public Set<String> getCacheNames(final String contextUrl, final String jndiName) throws CacheManagerException{
		
		DefaultCacheContainer defaultContainer = getContainerByJNDI(contextUrl, jndiName);
		
		if(defaultContainer == null)
			throw new CacheManagerException("Cache container not set");
		
		Set<String> cacheNamesSet = defaultContainer.getCacheNames();
		
		if(cacheNamesSet == null || cacheNamesSet.isEmpty())
			throw new CacheManagerException("No cache available");
			
		return cacheNamesSet;
	}

	@Override
	public Object getKeyValue(String key) {
		return _cache.get(key);
	}

	@Override
	public void setKeyValue(String key, Object value) {
		_cache.put(key,value);
	}

}
