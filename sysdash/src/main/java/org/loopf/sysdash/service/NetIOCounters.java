package org.loopf.sysdash.service;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.loopf.sysdash.util.CommonUtil;

/**
 * 
 * @author liuzhh
 *
 */
public class NetIOCounters {
	
	private static List<Properties> lastData = null;
	
	private static long lastRequestTime = 0L;
	
	private static void set(List<Properties> info){
		lastData = info;
		lastRequestTime = System.currentTimeMillis();
	}
	
	public static Properties[] update(List<Properties> info){
		Properties[] nowArray = info.toArray(new Properties[0]);
		if( lastData == null ){
			set(info);
			for (int i = 0; i < nowArray.length; i++) {
				nowArray[i].setProperty("rx_per_sec", CommonUtil.roundFileSizeFormat(0, 1));
				nowArray[i].setProperty("tx_per_sec", CommonUtil.roundFileSizeFormat(0, 1));
			}
			return nowArray;
		}
		long time_delta = System.currentTimeMillis() - lastRequestTime;
		for (int i = 0; i < nowArray.length; i++) {
			for (Iterator<Properties> iterator = lastData.iterator(); iterator.hasNext();) {
				Properties nicCounterInfo = (Properties) iterator
						.next();
				if( nowArray[i].getProperty("index").equals(nicCounterInfo.get("index")) ){
					long bytes_recv = Long.parseLong(nowArray[i].getProperty("inOctets"));
					long last_bytes_recv = Long.parseLong(nicCounterInfo.get("inOctets").toString());
					long bytes_sent = Long.parseLong(nowArray[i].getProperty("outOctets"));
					long last_bytes_sent = Long.parseLong(nicCounterInfo.get("outOctets").toString());
					double rx = (bytes_recv - last_bytes_recv)/(time_delta/1000L);
					double tx = (bytes_sent - last_bytes_sent)/(time_delta/1000L);
					nowArray[i].setProperty("rx_per_sec", CommonUtil.roundFileSizeFormat(rx, 1));
					nowArray[i].setProperty("tx_per_sec", CommonUtil.roundFileSizeFormat(tx, 1));
				}
			}
		}
		return nowArray;
	}
	
	public static List<Properties> get(){
		return lastData;
	}
	
}
