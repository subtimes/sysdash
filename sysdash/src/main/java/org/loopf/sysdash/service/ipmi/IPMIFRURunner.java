package org.loopf.sysdash.service.ipmi;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author liuzhh
 *
 */
public class IPMIFRURunner extends AbstractIPMIRunner<Map<String, String>> {
	
	private static final String chassis = "fru";
	
	public IPMIFRURunner(String host, String user, String password) {
		super(host, user, password);
	}

	@Override
	public Map<String, String> getValues() {
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.putAll(ipmiToolProperty(chassis, null));
		return valueMap;
	}
	
	public static void main(String[] args) {
		IPMIFRURunner runner = new IPMIFRURunner("192.168.40.168", "administrator", "admin12345");
		System.out.println(runner.getValues());
	}

}
