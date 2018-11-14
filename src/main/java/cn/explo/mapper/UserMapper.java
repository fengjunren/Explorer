package cn.explo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.explo.pojo.User;

//@Mapper
public interface UserMapper {
	@Select("select * from user where userId = #{userId} or openId = #{openId}")
	User findById(String userId,String openId);
	
	@Insert("INSERT INTO user(openId) VALUES(#{openId})")
	int insert(String openId);
	
	@Update("UPDATE user SET nickName=#{nickName}, headImgUrl = #{headImgUrl} WHERE openId =#{openId}")
	int update(User user);
	
	@Update("UPDATE user SET isSubscribe=#{isSubscribe} WHERE openId =#{openId}")
	int updateStatus(int isSubscribe,String openId);
	
	@Delete("delete from user where openId = #{openId}")
	int delete(String openId);
	
}
