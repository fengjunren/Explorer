package cn.explo.utils;
 
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class MyJsonUtil {
	 private static ObjectMapper objectMapper = new ObjectMapper();  
	   static {
	        //去掉默认的时间戳格式  
	        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);  
//	        //设置为中国时区  
	        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
//	      //序列化时，日期的统一格式  
//	        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  
	   }
	    public static String serialize(Object object) throws JsonProcessingException {  
	        try {  
	            return objectMapper.writeValueAsString( object);  
	        } catch (Exception e) {   
	        }  
	        return "";  
	    }  
	    
	    public static byte[] serializeAsByte(Object object) throws JsonProcessingException {  
	        try {  
	            return objectMapper.writeValueAsBytes( object);  
	        } catch (Exception e) {   
	        }  
	        return null;  
	    }  
	  
	    /** 
	     * 将JSON字符串反序列化为对象 
	     *  
	     * @param object 
	     * @return JSON字符串 
	     * @throws IOException 
	     * @throws JsonMappingException 
	     * @throws JsonParseException 
	     */  
	    public static <T> T deserialize(String json, Class<T> clazz)  {  
	        Object object = null;  
	        try {  
	            object = objectMapper.readValue(json, TypeFactory.rawClass(clazz)); 
	           
	        } catch (Exception e) {   
	        	String msg=e.getMessage();
	        }  
	        return (T) object;  
	    }  
	    
	    public static <T> T deserialize(InputStream inputStream, Class<T> clazz)  {  
	        Object object = null;  
	        try {  
	            object = objectMapper.readValue(inputStream, TypeFactory.rawClass(clazz)); 
	           
	        } catch (Exception e) {   
	        	String msg=e.getMessage();
	        }  
	        return (T) object;  
	    }  
	    
	    public static <T> T deserialize(String json, TypeReference<T> types)  {  
	        Object object = null;  
	        String msg="";
	        try {  
	            object = objectMapper.readValue(json, types);  
	        } catch (Exception e) {  
	        	msg="JsonUtils.deserialize:"+e.getMessage()+" "+e.getStackTrace();
	        }  
	        return (T) object;  
	    }  
	    
	    public static <T> T deserialize(File file, TypeReference<T> types){  
	        Object object = null;  
	        String msg="";
	        try {  
	            object = objectMapper.readValue(file, types);  
	        } catch (Exception e) {  
	        	msg="JsonUtils.deserialize:"+e.getMessage()+" "+e.getStackTrace();
	        }  
	        return (T) object;  
	    }  
	    
	    public static <T> T convert(Object fromValue,Class<T> toValueType) {
	    	return objectMapper.convertValue(fromValue, toValueType);
	    }
	    
}
