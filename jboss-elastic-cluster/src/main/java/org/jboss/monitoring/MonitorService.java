package org.jboss.monitoring;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jboss.aop.microcontainer.aspects.jmx.JMX;
import org.jboss.ejb3.annotation.Management;
import org.jboss.jmx.adaptor.rmi.RMIAdaptor;
import org.jboss.monitor.JBossMonitor;
import org.jgroups.Address;
import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;

/**
 * Service monitor to manage nodes in cluster and start new ones on-demand.
 * Samuel Tauil (samueltauil@gmail.com)
 */
//@Management(MonitorServiceMBean.class)
//@JMX(name="jboss.monitoring:service=MonitorService", exposedInterface=org.jboss.monitoring.MonitorServiceMBean.class, registerDirectly=true)
public class MonitorService extends JBossMonitor implements MonitorServiceMBean, NotificationListener {

	//TODO: implements Job interface and add config file to the project.
	//TODO: retrieve all services in managed list to this service, cause could be more than one to define VM startup.
	//TODO: implements NotificationListener, services must be implement NotificationBroadcasterSupport.
	
	private List<MBeanServerConnection> serverList;

	public static final String DEFAULT_JNDI_NAME = "jmx/invoker/RMIAdaptor";

	private String adapterName;

	private String serverURL;

	private String partitionName;
	
	static Logger logger = Logger.getLogger(MonitorService.class); 	

	public MonitorService(String adapterName, String serverURL) {
		this.adapterName = adapterName;
		this.serverURL = serverURL;
	}
	
	public MonitorService(String partitionName){
		this.partitionName = partitionName;
	}
	
	public String getAdapterName() {
		return adapterName;
	}

	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	protected List<MBeanServerConnection> createMBeanServerConnection() throws NamingException, MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, ChannelException {
		
				
		InitialContext ctx;

		if (serverURL == null) {
			ctx = new InitialContext();
		} else {
			Properties props = new Properties(System.getProperties());
			props.put(Context.PROVIDER_URL, serverURL);
			ctx = new InitialContext(props);
		}

		if (adapterName == null) {
			adapterName = DEFAULT_JNDI_NAME;
		}

		Object obj = ctx.lookup(adapterName);
		ctx.close();

		if (!(obj instanceof RMIAdaptor)) {
			throw new ClassCastException
			("Object not of type: RMIAdaptorImpl, but: " +
					(obj == null ? "not found" : obj.getClass().getName()));
		}

//		return (MBeanServerConnection) obj;
		
		ObjectName name = new ObjectName("jboss.jgroups:type=channel");
		MBeanServerConnection mBeanServerConnection = (MBeanServerConnection)obj;
		String attribute = (String) mBeanServerConnection.getAttribute(name, "Properties");
		JChannel channel = new JChannel(attribute);
		
		
		Vector<Address> members = channel.getView().getMembers();
		for (Address address : members) {
			address.toString();
		}
		
		return null;
	}

//	protected void connect() throws NamingException {
//		if (server == null) {
//			server = createMBeanServerConnection();
//		}
//	}


	@Override
	public void create() throws Exception {
		logger.info("Service MonitorService started");
		
	}

	@Override
	public void destroy() {
		logger.info("Service MonitorService stopped");		
	}
	
	
	public void execute() {
		
		try {
//			connect();
			
			ObjectName name = new ObjectName("jboss.system:type=ServerInfo");

			Connect conn = new Connect("qemu:///system", false);
			
			Domain domain = conn.domainLookupByName("rhel.img");
			
			domain.create();

			
			
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (LibvirtException e) {
			e.printStackTrace();
		}
		
		
	}


	@Override
	protected void testThreshold() {
		// TODO Auto-generated method stub
		
	}

	public void handleNotification(Notification notification, Object handback) {
		// TODO Auto-generated method stub
		
	}

}	   