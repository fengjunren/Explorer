package cn.explo.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.explo.mapper.DengMiMapper;
import cn.explo.pojo.DengMi;
import cn.explo.pojo.REP_MESSAGE;
import cn.explo.resolver.JsonParam;
import cn.explo.utils.MyJsonUtil;

@RestController
@RequestMapping(value="dengmi")
public class DengMiContro {
	private static final Logger logger = LoggerFactory.getLogger(DengMiContro.class);
	
	@Autowired
	DengMiMapper dmMapper;
	
	@RequestMapping(value = "/qryLargerId", method = RequestMethod.POST)
	public REP_MESSAGE qryWithId(@JsonParam(value = "$.REQ_BODY.id") int id) {
		DengMi dm =  dmMapper.findById(id);
		if(dm==null) {
			dm =  dmMapper.findById(0);
		}
		REP_MESSAGE rep_msg=new REP_MESSAGE();
		rep_msg.putRepBody(MyJsonUtil.convert(dm, HashMap.class));
		return rep_msg;
	}
	
}
