package cn.explo.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.explo.mapper.GuShiCiMapper;
import cn.explo.pojo.GuShiCi;
import cn.explo.pojo.REP_MESSAGE;
import cn.explo.resolver.JsonParam;
import cn.explo.utils.MyJsonUtil;

@RestController
@RequestMapping(value="gushici")
public class GuShiCiContro {
	private static final Logger logger = LoggerFactory.getLogger(GuShiCiContro.class);
	
	@Autowired
	GuShiCiMapper gscMapper;

	
	@RequestMapping(value = "/qryWithId", method = RequestMethod.POST)
	public REP_MESSAGE qryWithId(@JsonParam(value = "$.REQ_BODY.id") int id) {
		GuShiCi gsc =  gscMapper.findById(id);
		if(gsc==null) {
			gsc =  gscMapper.findById(1);
		}
		REP_MESSAGE rep_msg=new REP_MESSAGE();
		rep_msg.putRepBody(MyJsonUtil.convert(gsc, HashMap.class));
		return rep_msg;
	}
	
	@RequestMapping(value = "/qryRandom", method = RequestMethod.POST)
	public REP_MESSAGE qryRandom() {
		String getGscUrl = "https://api.gushi.ci/all.json";
		RestTemplate rest = new RestTemplate();
		GuShiCi gsc = rest.getForObject(getGscUrl, GuShiCi.class);
		logger.info("getGuSiCi--->>:" + gsc.toString());
		REP_MESSAGE rep_msg=new REP_MESSAGE();
		
		if (gsc != null) {
			gscMapper.insert(gsc);
			rep_msg.putRepBody(MyJsonUtil.convert(gsc, HashMap.class));
		}else {
			rep_msg.setTranCode("9999");
			rep_msg.setTranRspMsg("交易失败");
		}
		
		return rep_msg;
	}
	
}
