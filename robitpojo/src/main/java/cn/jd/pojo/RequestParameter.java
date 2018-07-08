package cn.jd.pojo;

import java.io.Serializable;

public class RequestParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String signature ;
	private String timestamp ;
	private String nonce ;
	private String echostr;
	
	
	public RequestParameter(String signature, String timestamp, String nonce, String echostr) {
		super();
		this.signature = signature;
		this.timestamp = timestamp;
		this.nonce = nonce;
		this.echostr = echostr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getEchostr() {
		return echostr;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestParameter [signature=");
		builder.append(signature);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", nonce=");
		builder.append(nonce);
		builder.append(", echostr=");
		builder.append(echostr);
		builder.append("]");
		return builder.toString();
	}
	
	
}
