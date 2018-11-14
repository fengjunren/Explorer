package cn.explo.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.explo.converter.WxMappingJackson2HttpMessageConverter;
import cn.explo.mapper.GuShiCiMapper;
import cn.explo.mapper.UserMapper;
import cn.explo.mapper.WxConfigMapper;
import cn.explo.pojo.GuShiCi;
import cn.explo.pojo.User;
import cn.explo.utils.MyJsonUtil;
import cn.explo.utils.PropsUtil;

@Service
public class WxService {
	private static final Logger logger = LoggerFactory.getLogger(WxService.class);

	@Autowired
	UserMapper userMapper;
	@Autowired
	WxConfigMapper configMapper;
	@Autowired
	GuShiCiMapper gscMapper;

	public void updateUserInfo(String openId, String accessToken) {
		logger.info("---------------------1--------------------");
		RestTemplate rest = new RestTemplate();
		rest.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
//		// 查询用户信息
//		String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
//		Map map2 = rest.getForObject(String.format(getUserInfoUrl, accessToken, openId), Map.class);
		logger.info("---------------------2--------------------");
		String url = "https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token={access_token}&openid={openid}&lang={lang}";// tag7

		Map<String, String> params = new HashMap<>();
		params.put("access_token", accessToken);
		params.put("openid", openId);
		params.put("lang", "zh_CN");
		logger.info("开启请求微信用户信息...");
		Map map2 = rest.getForObject(url, Map.class, params);
		logger.info("getUserInfoUrl result-->:" + map2);
		if (map2.get("errcode") == null) {
			String nickName = map2.get("nickname") + "";
			String headImgUrl = map2.get("headimgurl") + "";
			User user = new User();
			user.openId = openId;
			user.headImgUrl = headImgUrl;
			user.nickName = nickName;
			userMapper.update(user);
		}
	}

	@Async
	public void sendTemplateMsg(String touser, String templateId) {
		String getGscUrl = "https://api.gushi.ci/all.json";
		RestTemplate rest = new RestTemplate();
		GuShiCi gsc = rest.getForObject(getGscUrl, GuShiCi.class);
		logger.info("getGuSiCi--->>:" + gsc.toString());
		if (gsc != null) {
			gscMapper.insert(gsc);

			String wxId = PropsUtil.getProperty("wxconfig.wxId");

			String accessToken = configMapper.findById(wxId).accessToken;
			String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

			// 设置header
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
			Map<String, Object> payload = new HashMap<>();
			payload.put("touser", touser);
			payload.put("template_id", templateId);
			String backEndUrl=PropsUtil.getProperty("backEndUrl");
			String url=backEndUrl+"authSilent/?pageId=1&bizId="+gsc.id;
			logger.info("消息模板填充的url:"+url);
			payload.put("url", url);

			Map<String, Object> content = new HashMap<>();
			content.put("value", gsc.content);
			content.put("color", "#173177");
			Map<String, Object> d1 = new HashMap<>();
			d1.put("content", content);
			payload.put("data", d1);

//        logger.info(payload.toString());
			// 设置参数
			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(payload, httpHeaders);

			ResponseEntity<HashMap> resp = rest.exchange(sendMsgUrl, HttpMethod.POST, requestEntity, HashMap.class);
            logger.info("sendTemplateMsg 响应结果:"+resp);
		}
	}
	public String getUrlParam(int pageId,int bizId) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("pageId", pageId);
		hm.put("bizId", bizId);
		try {
			return MyJsonUtil.serialize(hm);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
