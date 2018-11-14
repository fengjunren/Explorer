package cn.explo.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class WxMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7210324897502614708L;
	@XmlElement
	public String ToUserName;
	@XmlElement
	public String FromUserName;
	@XmlElement
	public long CreateTime;
	@XmlElement
	public String MsgType;
	@XmlElement
	public String Content;
	@XmlElement
	public long MsgId;
	@XmlElement
	public String Event;
	@XmlElement
	public String EventKey;
	@XmlElement
	public String Status;
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public void setContent(String content) {
		Content = content;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public void setStatus(String status) {
		Status = status;
	}
	@Override
	public String toString() {
		return "WxMsg [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime
				+ ", MsgType=" + MsgType + ", Content=" + Content + ", MsgId=" + MsgId + ", Event=" + Event
				+ ", EventKey=" + EventKey + ", Status=" + Status + "]";
	}
	 
	
	

}
