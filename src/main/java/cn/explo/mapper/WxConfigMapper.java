package cn.explo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.explo.pojo.WxConfig;

//@Mapper
public interface WxConfigMapper {
	@Select("select * from wx_config where wxId = #{wxId}")
	WxConfig findById(String wxId);
	
	@Update("UPDATE wx_config SET accessToken=#{accessToken}, updateTime=#{updateTime} WHERE wxId =#{wxId}")
	int updateToken(String wxId, String accessToken,String updateTime);
	
	@Update("UPDATE wx_config SET jsApiTicket=#{jsApiTicket}, updateTime=#{updateTime} WHERE wxId =#{wxId}")
	int updateTicket(String wxId, String jsApiTicket,String updateTime);
	
	@Delete("DELETE FROM wx_config WHERE wxId = #{wxId}")
    public int delete(String wxId);
}
