package cn.explo.pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown=true)
public class REP_MESSAGE {
    private Map<String, Object> REP_HEAD = new HashMap<String, Object>();
    
    private Map<String, Object> REP_BODY = new HashMap<String, Object>();
    
	public REP_MESSAGE (){
		setTranCode("000000");
		setTranRspMsg("交易成功");
	}
	
    @JsonProperty("REP_HEAD")
	public Map<String, Object> getREP_HEAD() {
		return REP_HEAD;
	}
    @JsonProperty("REP_BODY")
	public Map<String, Object> getREP_BODY() {
		return REP_BODY;
	}
    @JsonIgnore
	public String getSIGN () {
		return (String) REP_HEAD.get("SIGN");
	}
     
	public REP_MESSAGE setTranRspMsg(String TRAN_RSPMSG) {
		this.REP_HEAD.put("TRAN_RSPMSG", TRAN_RSPMSG);
		return this;
	}
    
	public REP_MESSAGE setTranCode(String TRAN_CODE) {
		this.REP_HEAD.put("TRAN_CODE", TRAN_CODE);
		return this;
	}
	public REP_MESSAGE putRepBody (Map<String, Object> REP_BODY) {
		this.REP_BODY.putAll(REP_BODY);
		return this;
	}
	public REP_MESSAGE addRepBodyFiled (String key,Object value) {
		this.REP_BODY.put(key, value);
		return this;
	}
	
//	public void genEncryptVal() {
//		String data;
//		try {
//			data = MyJsonUtil.serialize(REP_BODY_MAP);
//			this.REP_BODY=aesAlgorithm.encrypt(data, encryptKey);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	@Override
	public String toString() {
		return "REP_MESSAGE [REP_HEAD=" + REP_HEAD.toString() + ", REP_BODY=" + REP_BODY.toString() + "]";
	}
	public static void main(String[] args) {
		HashMap<String, String> m1= new HashMap<>();
		HashMap<String, String> m2= new HashMap<>();
		m1.put("k1", "1");
		m2.put("k1", "11");
		m2.put("k2", "2");
		m1.putAll(m2);
		System.out.println(m1);
	}
}
