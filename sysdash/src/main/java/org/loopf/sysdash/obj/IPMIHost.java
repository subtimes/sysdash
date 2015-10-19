package org.loopf.sysdash.obj;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author liuzhh
 *
 */
public class IPMIHost {

	private String ip;
	
	private String name;
	
	private String user;
	
	private String password;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
