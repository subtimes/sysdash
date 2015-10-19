package org.loopf.sysdash.service.ipmi;

import java.util.List;

/**
 * 
 * @author liuzhh
 *
 */
public class IPMISensorRunner extends AbstractIPMIRunner<List<String[]>> {
	
	private static final String sdr = "sdr";
	
	public IPMISensorRunner(String host, String user, String password) {
		super(host, user, password);
	}

	@Override
	public List<String[]> getValues() {
		return ipmiToolSdr(sdr, null);
	}

}
