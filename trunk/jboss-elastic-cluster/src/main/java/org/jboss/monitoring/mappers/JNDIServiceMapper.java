package org.jboss.monitoring.mappers;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIServiceMapper {
	
	public static <T> T JNDIServiceMapper(Class<T> clazz, final String contextUrl,final String jndiAdapterName) throws NamingException
	{
		InitialContext ctx;
		
		if (jndiAdapterName == null)
			throw new NamingException("JNDI adapter cannot be null");

		if (contextUrl == null || contextUrl.isEmpty()) 
		{
			ctx = new InitialContext();
		} 
		else 
		{	
			Properties props = new Properties(System.getProperties());
			props.put(Context.PROVIDER_URL, contextUrl);
			ctx = new InitialContext(props);
		}

		Object obj = ctx.lookup(jndiAdapterName);
		ctx.close();
		
		if(obj == null)
		{
			throw new NamingException
				("Object not of type: " + clazz + ", was not found on JNDI: " + jndiAdapterName);
		}
		
		try
		{
			T retClazz = clazz.cast(obj);
			return retClazz;
		}
		catch(ClassCastException cEx)
		{
	       throw new ClassCastException(
	               "Expected class " + clazz + " but was " + obj.getClass() );
		}
		
	}
}
