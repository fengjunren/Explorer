package cn.explo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.explo.pojo.DengMi;
import cn.explo.pojo.GuShiCi;

public interface DengMiMapper {
	@Select("SELECT * FROM deng_mi AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM deng_mi)-(SELECT MIN(id) FROM deng_mi))+(SELECT MIN(id) FROM deng_mi)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id LIMIT 1")
	DengMi findRandom();
	
	@Insert("INSERT INTO deng_mi(type,riddle,answer,hint) VALUES(#{type},#{riddle},#{answer},#{hint})")
	int insert(DengMi dm);
	
	@Select("SELECT * FROM deng_mi where id>#{id} limit 1")
	DengMi findById(int id);
	
}
