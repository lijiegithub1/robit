package cn.jd.service.impl;



import cn.jd.service.BaseProcess;

import java.util.Map;

public abstract class BaseProcessImpl implements BaseProcess {
	protected String fromUserName;
	protected String toUserName;
	protected Long createTime;
	protected String msgType;
	protected String msgId;
	
	public void init(Map<String, String> parseMap) {
		this.fromUserName = parseMap.get("FromUserName");
		this.toUserName = parseMap.get("ToUserName");
		this.createTime = Long.parseLong(parseMap.get("CreateTime"));//可以判断 消息收藏和删除使用
		this.msgType = parseMap.get("MsgType");//文档的类型，可以用作后面的判断 
		this.msgId = parseMap.get("MsgId"); //消息的id,可以将其放到数据库中进行存储使用
	}
	
	public abstract String process(Map<String, String> parseMap);
	
	
	
	

}
