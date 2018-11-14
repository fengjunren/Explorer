package cn.explo.converter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import cn.explo.utils.MyJsonUtil;

public class MyConverter extends AbstractHttpMessageConverter<Object> {
	 
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private static final Logger logger = LoggerFactory.getLogger(MyConverter.class);
    
    public MyConverter(){
        super(MediaType.APPLICATION_JSON_UTF8,
            new MediaType("application", "*+json", DEFAULT_CHARSET));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
    	logger.info("--------------MyConverter setup........");
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz,
                                  HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return MyJsonUtil.deserialize(decrypt(inputMessage.getBody()), clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(encrypt(MyJsonUtil.serializeAsByte(o)));
    }

    private InputStream decrypt(InputStream inputStream){
        // do your decryption here 
        return inputStream;
    }

    private byte[] encrypt(byte[] bytesToEncrypt){
        // do your encryption here 
        return bytesToEncrypt;
    }

}
