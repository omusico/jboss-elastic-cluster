package org.jboss.monitoring.serviceTest;


interface InfinispanConnTest {

	public void setJNDIDistributedKV(String key, String value);
	public Object getJNDIDistributedKV(String key) ;

	public void setJMXDistributedKV(String key, String value);
	public Object getJMXDistributedKV(String key) ;
	
	// Life cycle method
	public void create () throws Exception;
	public void destroy () throws Exception; 
}
