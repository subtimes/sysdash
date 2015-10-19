package org.loopf.sysdash.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcCred;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ProcFd;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Who;
import org.loopf.sysdash.obj.IPMIHost;
import org.loopf.sysdash.service.HostConfiguration;
import org.loopf.sysdash.service.NetIOCounters;
import org.loopf.sysdash.service.SigarUtil;
import org.loopf.sysdash.service.ipmi.IPMIChassisRunner;
import org.loopf.sysdash.service.ipmi.IPMIFRURunner;
import org.loopf.sysdash.service.ipmi.IPMISensorRunner;
import org.loopf.sysdash.service.ipmi.IPMISessionRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class SigarController {

	@RequestMapping("/main")
	public static String mainAction(
			@RequestHeader(value = "X-Requested-With", defaultValue = "NONE") String requestWith,
			ModelMap map) {
		Sigar sigar = new Sigar();
		try {
			SigarUtil.appendCommonProperties(sigar, map, requestWith,
					"overview");
			SigarUtil.appendCPUProperties(sigar, map);
			SigarUtil.appendMEMProperties(sigar, map);

			List<Properties> info = SigarUtil.getNetworkInfos(sigar);
			map.addAttribute("interfaces", NetIOCounters.update(info));

			FileSystem[] fslist = sigar.getFileSystemList();
			map.addAttribute("disks", SigarUtil.getDiskInfos(sigar, fslist));

			Who[] users = sigar.getWhoList();
			map.addAttribute("users", users);
			return "/index.ftl";
		} catch (Exception e) {
			map.addAttribute("error", e);
			return "/error.ftl";
		} finally {
			if (sigar != null) {
				sigar.close();
			}
		}
	}

	@RequestMapping("/network")
	public static String networkAction(
			@RequestHeader(value = "X-Requested-With", defaultValue = "NONE") String requestWith,
			ModelMap map) {
		Sigar sigar = new Sigar();
		try {
			SigarUtil.appendCommonProperties(sigar, map, requestWith, "network");
			List<Properties> info = SigarUtil.getNetworkInfos(sigar);
			map.addAttribute("interfaces", NetIOCounters.update(info));
			return "/network.ftl";
		} catch (Exception e) {
			map.addAttribute("error", e);
			return "/error.ftl";
		} finally {
			if (sigar != null) {
				sigar.close();
			}
		}
	}

	@RequestMapping("/disk")
	public static String diskAction(
			@RequestHeader(value = "X-Requested-With", defaultValue = "NONE") String requestWith,
			ModelMap map) {
		Sigar sigar = new Sigar();
		try {
			SigarUtil.appendCommonProperties(sigar, map, requestWith, "disk");
			FileSystem[] arr = sigar.getFileSystemList();
			List<Properties> disks = SigarUtil.getDiskInfos(sigar, arr);
			map.addAttribute("disks", disks);
			List<Properties> diskios = SigarUtil.getDiskIOInfos(sigar, arr);
			map.addAttribute("diskios", diskios);
			return "/disks.ftl";
		} catch (Exception e) {
			map.addAttribute("error", e);
			return "/error.ftl";
		} finally {
			if (sigar != null) {
				sigar.close();
			}
		}
	}

	@RequestMapping("/processes")
	public static String processesAction(
			@RequestHeader(value = "X-Requested-With", defaultValue = "NONE") String requestWith,
			@RequestParam(value = "filter", required = false) String filter,
			ModelMap map) throws Exception {
		Sigar sigar = new Sigar();
		try {
			if (filter == null) {
				filter = "all";
			}
			SigarUtil.appendCommonProperties(sigar, map, requestWith,
					"processes");
			List<Properties> procList = SigarUtil.getProcessList(sigar, filter,
					map);
			map.addAttribute("processes", procList);
			map.addAttribute("filter", filter);
			return "/processes.ftl";
		} catch (Exception e) {
			map.addAttribute("error", e);
			return "/error.ftl";
		} finally {
			if (sigar != null) {
				sigar.close();
			}
		}
	}

	@RequestMapping("/process")
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static String processAction(
			@RequestHeader(value = "X-Requested-With", defaultValue = "NONE") String requestWith,
			@RequestParam(value = "pid", required = false) String pid,
			@RequestParam(value = "section", required = false) String section,
			ModelMap map) {
		Sigar sigar = new Sigar();
		try {
			SigarUtil
					.appendCommonProperties(sigar, map, requestWith, "process");
			if (section == null) {
				section = "overview";
			}
			Properties prop = new Properties();
			try {
				ProcCred cred = sigar.getProcCred(pid);
				ProcCredName credName = sigar.getProcCredName(pid);
				prop.setProperty("user", credName.getUser());
				prop.setProperty("uid_real", String.valueOf(cred.getUid()));
				prop.setProperty("uid_effective",
						String.valueOf(cred.getEuid()));
				prop.setProperty("gid_real", String.valueOf(cred.getGid()));
				prop.setProperty("gid_effective",
						String.valueOf(cred.getEgid()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				prop.setProperty("user", "-");
				prop.setProperty("uid_real", "-");
				prop.setProperty("uid_effective", "-");
				prop.setProperty("gid_real", "-");
				prop.setProperty("gid_effective", "-");
			}

			try {
				ProcExe exec = sigar.getProcExe(pid);
				prop.setProperty("cmdline", exec.getName());
				prop.setProperty("cwd", exec.getCwd());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				prop.setProperty("cmdline", "-");
				prop.setProperty("cwd", "-");
			}

			ProcState state = sigar.getProcState(pid);
			ProcFd fd = sigar.getProcFd(pid);

			try {
				ProcMem mem = sigar.getProcMem(pid);
				prop.setProperty("mem_rss", String.valueOf(mem.getRss()));
				prop.setProperty("mem_vms", String.valueOf(mem.getVsize()));
				prop.setProperty("mem_shared", String.valueOf(mem.getShare()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				prop.setProperty("mem_rss", "-");
				prop.setProperty("mem_vms", "-");
				prop.setProperty("mem_shared", "-");
			}

			try {
				ProcCpu cpu = sigar.getProcCpu(pid);
				prop.setProperty("cpu_times_user",
						String.valueOf(cpu.getUser()));
				prop.setProperty("cpu_times_system",
						String.valueOf(cpu.getSys()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				prop.setProperty("cpu_times_user", "-");
				prop.setProperty("cpu_times_system", "-");

			}

			try {
				Map env = sigar.getProcEnv(Long.valueOf(pid));
				map.addAttribute("process_environ", env);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				map.addAttribute("process_environ", new HashMap());
			}

			try {
				List modules = sigar.getProcModules(pid);
				map.addAttribute("files", modules);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				map.addAttribute("files", new ArrayList());
			}

			prop.setProperty("name", state.getName());
			prop.setProperty("pid", pid);
			prop.setProperty("num_threads", String.valueOf(state.getThreads()));
			prop.setProperty("num_files", String.valueOf(fd.getTotal()));
			if (state.getPpid() != 0) {
				ProcState pstate = sigar.getProcState(state.getPpid());
				prop.setProperty("parent_name", pstate.getName());
			}
			prop.setProperty("ppid", String.valueOf(state.getPpid()));
			prop.setProperty("status", String.valueOf(state.getState()));

			map.addAttribute("process", prop);
			map.addAttribute("section", section);
			return "process/" + section + ".ftl";
		} catch (Exception e) {
			map.addAttribute("error", e);
			return "/error.ftl";
		} finally {
			if (sigar != null) {
				sigar.close();
			}
		}
	}
	
	@RequestMapping("/ipmi")
	public static String fruAction(
					@RequestHeader(value = "X-Requested-With", defaultValue = "NONE") String requestWith,
					@RequestParam(value = "pid", required = false) String pid,
					@RequestParam(value = "section", required = false) String section,
					@RequestParam(value = "ip", required = false) String ip,
					ModelMap map) {
		Sigar sigar = new Sigar();
		try {
			SigarUtil.appendCommonProperties(sigar, map, requestWith, "ipmi");
			IPMIHost host = HostConfiguration.getHostByIP(ip);
			map.addAttribute("current_nodename", host.getName());
			map.addAttribute("current_nodeip", host.getIp());
			map.addAttribute("hosts", HostConfiguration.getHosts());
			if (section == null) {
				section = "fru";
			}
			map.addAttribute("section", section);
			if( section.equals("fru") ){
				IPMIFRURunner fru = new IPMIFRURunner(host.getIp(), host.getUser(), host.getPassword());
				Map<String, String> valueMap = fru.getValues();
				map.addAttribute("values", valueMap);
			}else if( section.equals("chassis") ){
				IPMIChassisRunner chassis = new IPMIChassisRunner(host.getIp(), host.getUser(), host.getPassword());
				Map<String, String> valueMap = chassis.getValues();
				map.addAttribute("values", valueMap);
			}else if( section.equals("session") ){
				IPMISessionRunner session = new IPMISessionRunner(host.getIp(), host.getUser(), host.getPassword());
				Map<String, String> valueMap = session.getValues();
				map.addAttribute("values", valueMap);
			}else if( section.equals("senssor") ){
				IPMISensorRunner senssor = new IPMISensorRunner(host.getIp(), host.getUser(), host.getPassword());
				List<String[]> valueMap = senssor.getValues();
				map.addAttribute("values", valueMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.addAttribute("error", e);
			return "/error.ftl";
		} finally {
			if (sigar != null) {
				sigar.close();
			}
		}
		if( section.equals("senssor")){
			return "ipmi/value_status.ftl";
		}else{
			return "ipmi/values.ftl";
		}
	}

}
