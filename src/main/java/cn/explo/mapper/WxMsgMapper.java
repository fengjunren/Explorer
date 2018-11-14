package cn.explo.mapper;

import org.apache.ibatis.annotations.Insert;

import cn.explo.pojo.WxMsg;

public interface WxMsgMapper {
 
	@Insert("INSERT INTO wx_msg VALUES(#{ToUserName},#{FromUserName},#{CreateTime},#{MsgType},#{Content},#{MsgId},#{Event},#{EventKey},#{Status})")
	int insert(WxMsg wxMsg);
	
}
