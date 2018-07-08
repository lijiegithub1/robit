package cn.jd.pojo;

import java.io.Serializable;

public class AccessToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String access_token;
	private final Integer expires_in;
	
	public AccessToken(String access_token, Integer expires_in) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
	}

	public String getAccess_token() {
		return access_token;
	}
	
	public Integer getExpires_in() {
		return expires_in;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccessToken [access_token=");
		builder.append(access_token);
		builder.append(", expires_in=");
		builder.append(expires_in);
		builder.append("]");
		return builder.toString();
	}
	
	
}
