package cn.explo.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.explo.config.Constant;
import cn.explo.mapper.GuShiCiMapper;
import cn.explo.mapper.UserMapper;
import cn.explo.mapper.WxConfigMapper;
import cn.explo.mapper.WxMsgMapper;
import cn.explo.pojo.REP_MESSAGE;
import cn.explo.pojo.User;
import cn.explo.pojo.WxConfig;
import cn.explo.pojo.WxMsg;
import cn.explo.pojo.WxPara;
import cn.explo.resolver.JsonParam;
import cn.explo.service.WxService;
import cn.explo.utils.CommonUtil;
import cn.explo.utils.EncryptUtil;
import cn.explo.utils.MyJsonUtil;
import cn.explo.utils.PropsUtil;

@RestController
public class WxContro {
	private String wxId = PropsUtil.getProperty("wxconfig.wxId");
	private String templateId = PropsUtil.getProperty("wxconfig.templateId");
	private static final Logger logger = LoggerFactory.getLogger(WxContro.class);
	@Autowired
	WxConfigMapper configMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	GuShiCiMapper gscMapper;
	@Autowired
	WxService wxService;
	@Autowired
	WxMsgMapper wxMsgMapper;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public void hello(HttpServletResponse response) {
		System.out.println("=====================");
		logger.info("----------------------------hello{}", "33333");
		try {
			CommonUtil.write(response, "hello");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getWxConfig", method = RequestMethod.POST)
	public WxConfig config() {
		WxConfig config = configMapper.findById(wxId);
		return config;
	}

	@RequestMapping(value = "/getWxPara", method = RequestMethod.POST)
	public REP_MESSAGE getPara(@JsonParam(value = "$.REQ_BODY.url") String url) {
		WxConfig config = configMapper.findById(wxId);
		WxPara p=new WxPara();
		p.appId=config.appId;
		p.nonceStr= UUID.randomUUID().toString().replace("-", "").substring(0, 16);// 随机字符串
		p.timestamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳
		String str = "jsapi_ticket="+config.jsApiTicket+"&noncestr="+p.nonceStr+"&timestamp="+p.timestamp+"&url="+url;  
		logger.info(""+str);
		p.signature =EncryptUtil.getSha1(str);  
		REP_MESSAGE rep_msg = new REP_MESSAGE();
		rep_msg.putRepBody(MyJsonUtil.convert(p, HashMap.class));
		return rep_msg;
	}

	@RequestMapping(value = "/authSilent", method = RequestMethod.GET)
	public void authSilent(int pageId, int bizId, HttpServletResponse response) throws IOException {
		System.out.println("接收参数pageId:" + pageId + ",bizId:" + bizId);
		WxConfig config = this.config();
		String frontEndUrl = PropsUtil.getProperty("frontEndUrl");
		String redirectUrl = frontEndUrl + wxService.getUrlParam(pageId, bizId);
		redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
		String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&response_type=code&scope=snsapi_userinfo&state=STATE&redirect_uri=%s";
		String toWxUrl = String.format(authUrl, config.appId, redirectUrl);
		logger.info("toWxUrl:" + toWxUrl);
		response.sendRedirect(toWxUrl);
	}

	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	public REP_MESSAGE getUserInfo(@JsonParam(value = "$.REQ_BODY.code") String code) {
		// 1.code换取access_token,openid
		// 2.access_token,openid 查询用户信息
		// *这里的access_token 与定时器更新的access_token不同
		REP_MESSAGE rep_msg = new REP_MESSAGE();
		User resU = new User();
		RestTemplate rest = new RestTemplate();
		String getTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
		WxConfig config = this.config();
		String url = String.format(getTokenUrl, config.appId, config.appSecret, code);
		String res = rest.getForObject(url, String.class);
		logger.info("url:" + url);
		logger.info("access_token result:", res);
		if (res != null && !res.isEmpty()) {
			HashMap map = MyJsonUtil.deserialize(res, HashMap.class);
			if (map.get("errcode") == null) {
				String accessToken = map.get("access_token") + "";
				String openId = map.get("openid") + "";
				wxService.updateUserInfo(openId, accessToken);
				resU = userMapper.findById("", openId);
				resU.accessToken = config.accessToken;
			} else {
				rep_msg.setTranCode(map.get("errcode") + "");
				rep_msg.setTranRspMsg(map.get("errmsg") + "");
			}
		} else {
			rep_msg.setTranCode("10002");
			rep_msg.setTranRspMsg("交易失败！");
		}
		Map map2 = MyJsonUtil.convert(resU, HashMap.class);
		rep_msg.putRepBody(map2);
		return rep_msg;
	}

	@RequestMapping(value = "/setMenu", method = RequestMethod.POST)
	public REP_MESSAGE setMenu(@RequestBody Map<String, Object> payload) {
		REP_MESSAGE rep_msg = new REP_MESSAGE();
		String ACCESS_TOKEN = this.config().accessToken;
		String setMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + ACCESS_TOKEN;

		// 设置header
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");

		// 设置参数
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(payload, httpHeaders);

		// 执行请求
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<HashMap> resp = restTemplate.exchange(setMenuUrl, HttpMethod.POST, requestEntity, HashMap.class);
		logger.info(resp.toString());
		Map res = resp.getBody();
		logger.info(res.toString());
		logger.info((Integer) res.get("errcode") + "");
		rep_msg.putRepBody(res);
		return rep_msg;
	}

	@RequestMapping(value = "/getMenu", method = RequestMethod.GET)
	public String getMenu() throws IOException {
		WxConfig config = this.config();
		String getMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + config.accessToken;
		RestTemplate rest = new RestTemplate();
		String result = rest.getForObject(getMenuUrl, String.class);

		return result;
	}

	@RequestMapping(value = "/msgReply")
	public @ResponseBody WxMsg msgReply(@RequestBody WxMsg payload, HttpServletResponse response) {
		logger.info("payload:" + payload.toString());
		wxMsgMapper.insert(payload);// 记录一下

		WxMsg resXml = new WxMsg();
		resXml.FromUserName = payload.ToUserName;
		resXml.ToUserName = payload.FromUserName;
		resXml.CreateTime = System.currentTimeMillis();
//		logger.info(payload + "");
		if (payload != null) {
			String msgType = payload.MsgType;
			resXml.MsgType = msgType;
			switch (msgType) {
			case "event":
				logger.info("case event:");
				String Event = payload.Event;
				resXml.Event = Event;
				if ("subscribe".equals(Event)) {
					// 用户关注立即新增一个用户
					String _openId = payload.FromUserName;
					userMapper.insert(_openId);// 新增一个用户

					wxService.sendTemplateMsg(payload.FromUserName, templateId);
				} else if ("unsubscribe".equals(Event)) {
					// 用户取关 则删除用户
					userMapper.delete(payload.FromUserName);
				} else if ("CLICK".equals(Event)) {
					logger.info("CLICK Event:");
					String eventKey = payload.EventKey;
					resXml.EventKey = eventKey;
//					logger.info("eventKey.equals(getGuSiCi):"+eventKey.equals("getGuSiCi"));
					if (eventKey.equals("getGuSiCi")) {
						try {
							CommonUtil.write(response, " ");// 响应空串给微信
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						wxService.sendTemplateMsg(payload.FromUserName, templateId);
					}
				}
				break;
			case "text":
				String content = payload.Content;
				resXml.Content = "您输入的内容:\n" + content;
				break;

			default:
				break;
			}

		}
		wxMsgMapper.insert(resXml);// 记录一下
		logger.info("resp msg------>>>>:" + resXml.toString());
		return resXml;
	}

}
