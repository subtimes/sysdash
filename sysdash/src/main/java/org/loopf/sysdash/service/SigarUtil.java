package org.loopf.sysdash.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.loopf.sysdash.util.CommonUtil;
import org.springframework.ui.ModelMap;

public class SigarUtil {

	public static void appendCommonProperties(Sigar sigar, ModelMap map,
			String requestWith, String page) throws Exception {
		OperatingSystem sys = OperatingSystem.getInstance();
		map.addAttribute("requestWith", requestWith);
		map.addAttribute("hostname", CommonUtil.getHostName());
		map.addAttribute("uptime",
				CommonUtil.formatUptime(sigar.getUptime().getUptime()));
		map.addAttribute("os", sys.getDescription());
		map.addAttribute("page", page);
	}

	public static void appendCPUProperties(Sigar sigar, ModelMap map)
			throws Exception {
		CpuInfo[] infos = sigar.getCpuInfoList();
		CpuPerc perc = sigar.getCpuPerc();
		map.addAttribute("cpu_load", perc.getCombined());
		map.addAttribute("cpu_user", perc.getUser());
		map.addAttribute("cpu_system", perc.getSys());
		map.addAttribute("cpu_idle", perc.getIdle());
		map.addAttribute("num_cpus", infos[0].getTotalCores());
		map.addAttribute("cpu_vender", infos[0].getVendor());
		map.addAttribute("cpu_model", infos[0].getModel());
	}

	public static void appendMEMProperties(Sigar sigar, ModelMap map)
			throws Exception {
		Mem mem = sigar.getMem();
		map.addAttribute("mem_total", CommonUtil.toGB(mem.getTotal()));
		map.addAttribute("mem_avail", CommonUtil.toGB(mem.getActualFree()));
		map.addAttribute("mem_used", CommonUtil.toGB(mem.getUsed()));
		map.addAttribute("mem_free", CommonUtil.toGB(mem.getFree()));
		map.addAttribute("mem_used_perc",
				CommonUtil.round((100D - mem.getFreePercent()), 2));
	}

	public static void calUsedPercent(List<Properties> list) {
		if (list != null && !list.isEmpty()) {
			for (Iterator<Properties> iterator = list.iterator(); iterator
					.hasNext();) {
				Properties properties = (Properties) iterator.next();
				String total = (String) properties.get("total");
				String free = (String) properties.get("free");
				if (total == null || free == null) {
					properties.setProperty("total", "-");
					properties.setProperty("free", "-");
					properties.setProperty("used", "-");
					properties.setProperty("usedpercent", "-");
					continue;
				}
				properties.setProperty(
						"total",
						CommonUtil.roundFileSizeFormat(
								Double.parseDouble(total), 1));
				properties.setProperty("free", CommonUtil.roundFileSizeFormat(
						Double.parseDouble(free), 1));
				long used = Long.parseLong(total) - Long.parseLong(free);
				properties
						.setProperty(
								"used",
								CommonUtil.roundFileSizeFormat(
										Double.valueOf(used), 1));
				double perc = Double.valueOf(used) / Double.valueOf(total);
				properties.setProperty("usedpercent",
						String.valueOf(CommonUtil.round((perc * 100D), 1)));
			}
		}
	}

	public static List<Properties> getProcessList(Sigar sigar, String filter,
			ModelMap map) throws Exception {
		long[] pids = sigar.getProcList();
		List<Properties> procList = new ArrayList<Properties>();
		List<Properties> userProcList = new ArrayList<Properties>();
		for (int i = 0; i < pids.length; i++) {
			Properties p = getProcessInfo(sigar, pids[i]);
			String user = (String) p.get("user");
			if (!user.equalsIgnoreCase("system")
					&& !user.equalsIgnoreCase("unknown")) {
				userProcList.add(p);
			}
			procList.add(p);
		}
		map.addAttribute("num_procs", procList.size());
		map.addAttribute("num_user_procs", userProcList.size());
		if (filter.equals("user")) {
			return userProcList;
		} else {
			return procList;
		}
	}

	private static String getStartTime(long time) {
		if (time == 0) {
			return "00:00";
		}
		long timeNow = System.currentTimeMillis();
		String fmt = "MMMd";

		if ((timeNow - time) < ((60 * 60 * 24) * 1000)) {
			fmt = "HH:mm";
		}

		return new SimpleDateFormat(fmt).format(new Date(time));
	}

	public static List<Properties> getNetworkInfos(SigarProxy sigar)
			throws SigarException {
		List<Properties> nicList = new ArrayList<Properties>();
		String[] ifNames = sigar.getNetInterfaceList();
		for (int i = 0; i < ifNames.length; i++) {
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(ifNames[i]);
			Properties prop = new Properties();
			if (!NetFlags.NULL_HWADDR.equals(ifconfig.getHwaddr())) {
				prop.setProperty("macAddress", ifconfig.getHwaddr());
	        }
			prop.setProperty("nicName", ifconfig.getDescription());
			prop.setProperty("index", ifconfig.getName());
			prop.setProperty("ipAddress", ifconfig.getAddress());
			prop.setProperty("ifType", ifconfig.getType());
			prop.setProperty("status", NetFlags.getIfFlagsString(ifconfig.getFlags()));
			try {
				NetInterfaceStat ifstat = sigar.getNetInterfaceStat(ifNames[i]);
				prop.setProperty("speed", String.valueOf(ifstat.getSpeed()));
				prop.setProperty("outOctets", String.valueOf(ifstat.getTxBytes()));
				prop.setProperty("inOctets", String.valueOf(ifstat.getRxBytes()));
				prop.setProperty("outUcastPkts", String.valueOf(ifstat.getTxPackets()));
				prop.setProperty("inUcastPkts", String.valueOf(ifstat.getRxPackets()));
				prop.setProperty("inErrors", String.valueOf(ifstat.getRxErrors()));
				prop.setProperty("outErrors", String.valueOf(ifstat.getTxErrors()));
				prop.setProperty("inDiscards", String.valueOf(ifstat.getRxDropped()));
				prop.setProperty("outDiscards", String.valueOf(ifstat.getTxDropped()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			nicList.add(prop);
		}
		return nicList;
	}

	@SuppressWarnings("deprecation")
	private static Properties getProcessInfo(SigarProxy sigar, long pid)
			throws SigarException {

		ProcState state = sigar.getProcState(pid);
		ProcTime time = null;

		Properties info = new Properties();
		info.setProperty("pid", String.valueOf(pid));

		try {
			ProcCredName cred = sigar.getProcCredName(pid);
			info.setProperty("user", cred.getUser());
		} catch (SigarException e) {
			info.setProperty("user", "unknown");
		}

		try {
			time = sigar.getProcTime(pid);
			info.setProperty("start_time", getStartTime(time.getStartTime()));
		} catch (SigarException e) {
			info.setProperty("start_time", "unknown");
		}

		try {
			ProcMem mem = sigar.getProcMem(pid);
			info.setProperty("mem_size", Sigar.formatSize(mem.getSize()));
			info.setProperty("mem_rss", Sigar.formatSize(mem.getRss()));
			info.setProperty("mem_share", Sigar.formatSize(mem.getShare()));
		} catch (SigarException e) {
			info.setProperty("mem_size", "unknown");
			info.setProperty("mem_rss", "unknown");
			info.setProperty("mem_share", "unknown");
		}

		info.setProperty("state", String.valueOf(state.getState()));

		if (time != null) {
			info.setProperty("cpu_time", getCpuTime(time));
		} else {
			info.setProperty("cpu_time", "unknown");
		}

		String name = ProcUtil.getDescription(sigar, pid);
		info.setProperty("name", name);
		info.setProperty("pwd", state.getName());

		return info;
	}

	private static String getCpuTime(long total) {
		long t = total / 1000;
		return t / 60 + ":" + t % 60;
	}

	private static String getCpuTime(ProcTime time) {
		return getCpuTime(time.getTotal());
	}

	public static List<Properties> getDiskIOInfos(Sigar sigar,
			FileSystem[] fsArray) throws SigarException {
		List<Properties> infos = new ArrayList<Properties>();
		for (int i = 0; i < fsArray.length; i++) {
			if (fsArray[i].getType() != FileSystem.TYPE_LOCAL_DISK) {
				continue;
			}
			FileSystemUsage usage = sigar.getFileSystemUsage(fsArray[i]
					.getDirName());
			Properties prop = new Properties();
			prop.setProperty("name", fsArray[i].getDevName());
			prop.setProperty("dir", fsArray[i].getDirName());
			prop.setProperty("read_count", String.valueOf(usage.getDiskReads()));
			prop.setProperty("write_count",
					String.valueOf(usage.getDiskWrites()));
			if (usage.getDiskReadBytes() == Sigar.FIELD_NOTIMPL) {
				prop.setProperty("bytes_read", "-");
				prop.setProperty("bytes_written", "-");
			} else {
				prop.setProperty("bytes_read", CommonUtil.roundFileSizeFormat(
						usage.getDiskReadBytes(), 1));
				prop.setProperty(
						"bytes_written",
						CommonUtil.roundFileSizeFormat(
								usage.getDiskWriteBytes(), 1));
			}

			if (usage.getDiskQueue() == Sigar.FIELD_NOTIMPL) {
				prop.setProperty("queue", "-");
			} else {
				prop.setProperty("queue", String.valueOf(usage.getDiskQueue()));
			}
			if (usage.getDiskServiceTime() == Sigar.FIELD_NOTIMPL) {
				prop.setProperty("disk_time", "-");
			} else {
				prop.setProperty("disk_time",
						String.valueOf(usage.getDiskServiceTime()));
			}
			infos.add(prop);
		}
		return infos;
	}

	public static List<Properties> getDiskInfos(Sigar sigar,
			FileSystem[] fsArray) throws SigarException {
		long used, avail, total, pct;
		List<Properties> infos = new ArrayList<Properties>();
		for (int i = 0; i < fsArray.length; i++) {
			try {
				FileSystemUsage usage;
				if (fsArray[i] instanceof NfsFileSystem) {
					NfsFileSystem nfs = (NfsFileSystem) fsArray[i];
					if (!nfs.ping()) {
						continue;
					}
				}
				usage = sigar.getFileSystemUsage(fsArray[i].getDirName());
				used = usage.getTotal() - usage.getFree();
				avail = usage.getAvail();
				total = usage.getTotal();
				pct = (long) (usage.getUsePercent() * 100);
			} catch (SigarException e) {
				used = avail = total = pct = 0;
			}
			String usePct;
			if (pct == 0) {
				usePct = "-";
			} else {
				usePct = pct + "%";
			}
			Properties p = new Properties();
			p.setProperty("drive_letter", fsArray[i].getDevName());
			p.setProperty("total",
					CommonUtil.roundFileSizeFormat(total * 1024D, 1));
			p.setProperty("used",
					CommonUtil.roundFileSizeFormat(used * 1024D, 1));
			p.setProperty("free",
					CommonUtil.roundFileSizeFormat(avail * 1024D, 1));
			p.setProperty("usedpercent", usePct);
			p.setProperty("dir", fsArray[i].getDirName());
			p.setProperty("opts", fsArray[i].getOptions());
			p.setProperty("type", fsArray[i].getSysTypeName() + "/"
					+ fsArray[i].getTypeName());

			infos.add(p);
		}
		return infos;
	}

}
