package cn.explo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.explo.pojo.DengMi;
import cn.explo.pojo.GuShiCi;

public interface GuShiCiMapper {
	@Select("SELECT * FROM gu_shi_ci AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM gu_shi_ci)-(SELECT MIN(id) FROM gu_shi_ci))+(SELECT MIN(id) FROM gu_shi_ci)) AS id) AS t2 WHERE t1.id >= t2.id ORDER BY t1.id LIMIT 1")
	GuShiCi findRandom();
	
	@Select("SELECT * FROM gu_shi_ci where id=#{id} limit 1")
	GuShiCi findById(int id);
	
	@Insert("INSERT INTO gu_shi_ci(content,origin,author,category,c1,c2,c3) VALUES(#{content},#{origin},#{author},#{category},#{c1},#{c2},#{c3})")
	@Options(useGeneratedKeys= true, keyProperty="id")
	int insert(GuShiCi gushci);
	
	
	
}
