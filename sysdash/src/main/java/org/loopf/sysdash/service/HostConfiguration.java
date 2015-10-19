package org.loopf.sysdash.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.loopf.sysdash.obj.IPMIHost;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * @author liuzhh
 *
 */
public class HostConfiguration {
	
	private static final String config_file = "hosts.xml";
	
	private static List<IPMIHost> hostList;
	
	static{
		try {
			hostList = new ArrayList<IPMIHost>();
			URL url = HostConfiguration.class.getClassLoader().getResource(config_file);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(url.openStream());
			NodeList nodes = doc.getElementsByTagName("host");
			if( nodes != null && nodes.getLength() > 0 ){
				for (int i = 0; i < nodes.getLength(); i++) {
					String name = ((Element)nodes.item(i)).getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
					String ip = ((Element)nodes.item(i)).getElementsByTagName("ip").item(0).getFirstChild().getNodeValue();
					String user = ((Element)nodes.item(i)).getElementsByTagName("user").item(0).getFirstChild().getNodeValue();
					String password = ((Element)nodes.item(i)).getElementsByTagName("password").item(0).getFirstChild().getNodeValue();
					IPMIHost host = new IPMIHost();
					host.setIp(ip);
					host.setName(name);
					host.setUser(user);
					host.setPassword(password);
					hostList.add(host);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<IPMIHost> getHosts(){
		return hostList;
	}
	
	public static IPMIHost getHostByIP(String ip){
		for (Iterator<IPMIHost> iterator = hostList.iterator(); iterator.hasNext();) {
			IPMIHost ipmiHost = iterator.next();
			if( ipmiHost.getIp().equals(ip) ){
				return ipmiHost;
			}
		}
		return hostList.get(0);
	}
	
}
