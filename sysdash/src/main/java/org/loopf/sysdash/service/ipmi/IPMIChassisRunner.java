package org.loopf.sysdash.service.ipmi;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author liuzhh
 *
 */
public class IPMIChassisRunner extends AbstractIPMIRunner<Map<String, String>> {
	
	private static final String chassis = "chassis";
	
	private static final String[] chassis_cmds = {"status"};

	public IPMIChassisRunner(String host, String user, String password) {
		super(host, user, password);
	}

	@Override
	public Map<String, String> getValues() {
		Map<String, String> valueMap = new HashMap<String, String>();
		for (int i = 0; i < chassis_cmds.length; i++) {
			valueMap.putAll(ipmiToolProperty(chassis, chassis_cmds[i]));
		}
		return valueMap;
	}
	
	public static void main(String[] args) {
		IPMIChassisRunner runner = new IPMIChassisRunner("192.168.40.168", "administrator", "admin12345");
		System.out.println(runner.getValues());
	}

}
