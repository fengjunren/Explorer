package cn.explo.pojo;

public class WxPara {
public String appId;
public String timestamp;
public String nonceStr;
public String signature;
@Override
public String toString() {
	return "WxPara [appId=" + appId + ", timestamp=" + timestamp + ", nonceStr=" + nonceStr + ", signature=" + signature
			+ "]";
}
 
}
