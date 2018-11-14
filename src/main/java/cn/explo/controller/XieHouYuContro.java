package cn.explo.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.explo.mapper.XieHouYuMapper;
import cn.explo.pojo.REP_MESSAGE;
import cn.explo.pojo.XieHouYu;
import cn.explo.resolver.JsonParam;
import cn.explo.utils.MyJsonUtil;

@RestController
@RequestMapping(value="xiehouyu")
public class XieHouYuContro {
	private static final Logger logger = LoggerFactory.getLogger(XieHouYuContro.class);
	
	@Autowired
	XieHouYuMapper xhyMapper;
	
	@RequestMapping(value = "/qryLargerId", method = RequestMethod.POST)
	public REP_MESSAGE qryWithId(@JsonParam(value = "$.REQ_BODY.id") int id) {
		XieHouYu xhy =  xhyMapper.findById(id);
		if(xhy==null) {
			xhy =  xhyMapper.findById(0);
		}
		REP_MESSAGE rep_msg=new REP_MESSAGE();
		rep_msg.putRepBody(MyJsonUtil.convert(xhy, HashMap.class));
		return rep_msg;
	}
	
}
