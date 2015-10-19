package org.loopf.sysdash.ipmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class IPMITest {
	
	private static final String user = "administrator";
	
	private static final String password = "admin12345";

	private static final String host = "192.168.40.168";
	
	private static final String executeable = findPath();
	
	private static final String prefix = executeable + " -I lanplus -U " + user + " -P " + password + " -H " + host;
	
	public static void main(String[] args) {
		System.out.println(ipmiToolProperty("chassis", "status"));
	}
	
	private static String findPath(){
		URL url = IPMITest.class.getClassLoader().getResource("lib/ipmitool.exe");
		return url.getFile();
	}

	private static Map<String, String> ipmiToolProperty(String command, String specCommand) {
		BufferedReader br = null;
		Map<String, String> valueMap = null;
		try {
			Process p = null;
			if( specCommand != null ){
				p = Runtime.getRuntime().exec(prefix + " " + command + " " + specCommand);
			}else{
				p = Runtime.getRuntime().exec(prefix + " " + command);
			}
			System.out.println("Exec started.");
			if(!p.waitFor(20, TimeUnit.SECONDS)){
				p.destroyForcibly();
				return new HashMap<String, String>();
			}
			System.out.println("Exec finished.");
			valueMap = new HashMap<String, String>();
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
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
	
	private static Map<String, String> ipmiToolTable(String command, String specCommand) {
		BufferedReader br = null;
		Map<String, String> valueMap = null;
		try {
			Process p = null;
			if( specCommand != null ){
				p = Runtime.getRuntime().exec(prefix + " " + command + " " + specCommand);
			}else{
				p = Runtime.getRuntime().exec(prefix + " " + command);
			}
			p.waitFor();
			valueMap = new HashMap<String, String>();
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
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
	
	private static String concat(String[] arr){
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < arr.length; i++) {
			sb.append(arr[i].trim());
			if( i != arr.length -1 ){
				sb.append(":");
			}
		}
		return sb.toString();
	}

}
