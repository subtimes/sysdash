package org.loopf.sysdash.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class CommonUtil {

	public static String toGB(long val){
		NumberFormat nf = DecimalFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		return nf.format((double)(val/1024D/1024D/1024D));
	}
	
	public static String round(double d, int digis){
		NumberFormat nf = DecimalFormat.getInstance();
		nf.setMaximumFractionDigits(digis);
		nf.setMinimumFractionDigits(digis);
		return nf.format(d);
	}
	
	public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
	
	public static String roundFileSizeFormat(double d, int digis){
		NumberFormat nf = DecimalFormat.getInstance();
		nf.setMaximumFractionDigits(digis);
		nf.setMinimumFractionDigits(digis);
		String unit = "Bytes";
		if( d > 1024 ){
			d = d / 1024D;
			unit = "kB";
		}
		if( d > 1024 ){
			d = d / 1024D;
			unit = "MB";
		}
		if( d > 1024 ){
			d = d / 1024D;
			unit = "GB";
		}
		return nf.format(d) + " " + unit;
	}
	
	public static String formatUptime(double uptime) {
		String retval = "";
		int days = (int) uptime / (60 * 60 * 24);
		int minutes, hours;
		if (days != 0) {
			retval += days + " " + ((days > 1) ? "days" : "day") + ", ";
		}
		minutes = (int) uptime / 60;
		hours = minutes / 60;
		hours %= 24;
		minutes %= 60;
		if (hours != 0) {
			retval += hours + ":" + minutes;
		} else {
			retval += minutes + " min";
		}
		return retval;
	}
	
	public static String toUpTime(double bootTime) {
		long l = (new Double(bootTime)).longValue() * 1000;
		long now = System.currentTimeMillis();
		long interval = (new Date(now).getTime() - new Date(l).getTime()) / 1000;// 秒
		long day = interval / (24 * 3600);// 天
		long hour = interval % (24 * 3600) / 3600;// 小时
		long minute = interval % 3600 / 60;// 分钟
		long second = interval % 60;// 秒
		return day + " days, " + hour + ":" + minute + ":" + second;
	}
	
}
