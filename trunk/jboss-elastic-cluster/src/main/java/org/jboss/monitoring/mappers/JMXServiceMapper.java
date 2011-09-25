package org.jboss.monitoring.mappers;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.NamingException;

import org.jboss.mx.util.MBeanProxyExt;
import org.jboss.mx.util.MBeanServerLocator;

public class JMXServiceMapper {

	public static <T> T getJMXObject(Class<T> clazz, final String jmxObjectPath) throws NamingException, MalformedObjectNameException, NullPointerException, InstanceNotFoundException, IOException
	{
		
		MBeanServer mbeanServer = MBeanServerLocator.locate();
		Object objInstance = MBeanProxyExt.create(clazz,
				jmxObjectPath, mbeanServer);

		try
		{
			T retClazz = clazz.cast(objInstance);
			return retClazz;
		}
		catch(ClassCastException cEx)
		{
	       throw new ClassCastException(
	               "Expected class " + clazz + " but was " + objInstance.getClass() );
		}

	}
	
	public static <T> T getJMXAtribute(final MBeanServerConnection mBeanServerConnection, Class<T> clazz,final String jmxObjectName,final String jmxAttributeName) throws NamingException, 
													MalformedObjectNameException, 
													NullPointerException, 
													AttributeNotFoundException, 
													InstanceNotFoundException, 
													MBeanException, 
													ReflectionException, 
													IOException
	{
		ObjectName name = new ObjectName(jmxObjectName);		
		Object objAttribute = mBeanServerConnection.getAttribute(name, jmxAttributeName);	
		
		if(objAttribute == null)
		{
			throw new NamingException
				("Object not of type: " + clazz + ", was not found on JMX: " + jmxObjectName);
		}
		
		try
		{
			T retClazz = clazz.cast(objAttribute);
			return retClazz;
		}
		catch(ClassCastException cEx)
		{
	       throw new ClassCastException(
	               "Expected class " + clazz + " but was " + objAttribute.getClass() );
		}
	}
	
}
