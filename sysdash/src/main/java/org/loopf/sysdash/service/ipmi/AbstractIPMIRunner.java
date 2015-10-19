package org.loopf.sysdash.service.ipmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author liuzhh
 *
 */
public abstract class AbstractIPMIRunner<T> {

	private final String executeable = findPath();

	final String prefix;

	public AbstractIPMIRunner(String host, String user, String password) {
		prefix = executeable + " -I lanplus -U " + user + " -P " + password + " -H " + host;
	}

	private static String findPath() {
		URL url = AbstractIPMIRunner.class.getClassLoader().getResource("lib/ipmitool.exe");
		return url.getFile();
	}
	
	protected List<String[]> ipmiToolSdr(String command, String specCommand) {
		BufferedReader br = null;
		List<String[]> valueList = null;
		try {
			Process p = null;
			if (specCommand != null) {
				p = Runtime.getRuntime().exec(prefix + " " + command + " " + specCommand);
			} else {
				p = Runtime.getRuntime().exec(prefix + " " + command);
			}
			if(!p.waitFor(20, TimeUnit.SECONDS)){
				p.destroyForcibly();
				return new ArrayList<String[]>();
			}
			valueList = new ArrayList<String[]>();
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				if( line.trim().equals("") ){
					continue;
				}
				String[] arr = line.split("\\|");
				valueList.add(trim(arr));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return valueList;
	}

	protected Map<String, String> ipmiToolProperty(String command, String specCommand) {
		BufferedReader br = null;
		Map<String, String> valueMap = null;
		try {
			Process p = null;
			if (specCommand != null) {
				p = Runtime.getRuntime().exec(prefix + " " + command + " " + specCommand);
			} else {
				p = Runtime.getRuntime().exec(prefix + " " + command);
			}
			if(!p.waitFor(20, TimeUnit.SECONDS)){
				p.destroyForcibly();
				return new HashMap<String, String>();
			}
			valueMap = new HashMap<String, String>();
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				if( line.trim().equals("") ){
					continue;
				}
				String[] arr = line.split(":");
				valueMap.put(arr[0].trim(), concat(arr));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return valueMap;
	}

	private String concat(String[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < arr.length; i++) {
			sb.append(arr[i].trim());
			if (i != arr.length - 1) {
				sb.append(":");
			}
		}
		return sb.toString();
	}
	
	private String[] trim(String[] arr) {
		String[] _arr = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			_arr[i] = arr[i].trim();
		}
		return _arr;
	}
	
	public abstract T getValues();

}
