package cn.explo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.explo.pojo.DengMi;
import cn.explo.pojo.XieHouYu;

public interface XieHouYuMapper {
	@Select("SELECT * FROM xie_hou_yu AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM xie_hou_yu)-(SELECT MIN(id) FROM xie_hou_yu))+(SELECT MIN(id) FROM xie_hou_yu)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id LIMIT 1")
	XieHouYu findRandom();
	
	@Insert("INSERT INTO xie_hou_yu(riddle,answer) VALUES(#{riddle},#{answer})")
	int insert(XieHouYu xhy);
	
	@Select("SELECT * FROM xie_hou_yu where id>#{id} limit 1")
	XieHouYu findById(int id);
}
