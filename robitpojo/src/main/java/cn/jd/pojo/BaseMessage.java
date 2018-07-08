package cn.jd.pojo;

import java.io.Serializable;

/**
 * 消息的基类
 * @author Administrator
 *
 */
public class BaseMessage implements Serializable{
	//开发者微信号
	protected String ToUserName;
	///开发者微信号
	protected String FromUserName; 
	//消息创建时间 （整型）
	protected Long CreateTime;
	//text 类型
	protected String MsgType;


	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	@Override
	public String toString() {
		return "BaseMessage{" +
				"ToUserName='" + ToUserName + '\'' +
				", FromUserName='" + FromUserName + '\'' +
				", CreateTime=" + CreateTime +
				", MsgType='" + MsgType + '\'' +
				'}';
	}
}
