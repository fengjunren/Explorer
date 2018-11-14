package cn.explo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cn.explo.mapper.WxConfigMapper;
import cn.explo.pojo.WxConfig;
import cn.explo.utils.PropsUtil;

/**
 * 注意这个类要和application 在同一级目录下
 */
@Component
public class WxSchedule {
	@Autowired
	WxConfigMapper mapper;
	private String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	private String jsapi_ticketUrl="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	private static String wxId = PropsUtil.getProperty("wxconfig.wxId");
	private static final Logger logger = LoggerFactory.getLogger(WxSchedule.class);

	@Scheduled(fixedRate = 90 * 60 * 1000) // 每90分钟获取token
	public void getAccessToken() {
		LocalDateTime localDateTime = LocalDateTime.now();
		String t = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		logger.debug("getAccessToken start: " + t);
		WxConfig config = mapper.findById(wxId);
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		Map map = restTemplate.getForObject(String.format(accessTokenUrl, config.appId, config.appSecret), Map.class);
		if (map != null)
			logger.info(map.toString());
		Object accessToken = map.get("access_token");
		if (accessToken != null&&!"".equals(accessToken)) {
			mapper.updateToken(wxId, accessToken.toString(), t);
		}
	}
	@Scheduled(fixedRate = 60 * 60 * 1000) // 每60分钟获取jsapi_ticket
	public void getJsapiTicket() {
		LocalDateTime localDateTime = LocalDateTime.now();
		String t = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		logger.debug("getJsapiTicket start: " + t);
		WxConfig config = mapper.findById(wxId);
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		Map map = restTemplate.getForObject(String.format(jsapi_ticketUrl, config.accessToken), Map.class);
		if (map != null)
			logger.info(map.toString());
		Object jsApiTicket = map.get("ticket");
		if (jsApiTicket != null&&!"".equals(jsApiTicket)) {
			mapper.updateTicket(wxId, jsApiTicket.toString(), t);
		}
	}
}