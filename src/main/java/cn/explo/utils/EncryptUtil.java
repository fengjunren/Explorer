package cn.explo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	public static String getSha1(String str){
	    if (null == str || 0 == str.length()){
	        return "";
	    }
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'a', 'b', 'c', 'd', 'e', 'f'};
	    try {
	        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	        mdTemp.update(str.getBytes("UTF-8"));
	         
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char[] buf = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            buf[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(buf);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
public static void main(String[] args) {
	String s="jsapi_ticket=HoagFKDcsGMVCIY2vOjf9iAhacaYrZMa3GLgYetF3YOn0VFXoFPhXrrDfC79PBdu8Q6rJ2BX-rFvH0lr3BUYog&noncestr=b47f222c61194427&timestamp=1541917120&url=https://www.wy180.cn/explo-wx/?code=071RUhMb1MDFMu0VCLLb1kE4Mb1RUhMq&state=STATE";
	System.out.println(getSha1(s));
}
}
