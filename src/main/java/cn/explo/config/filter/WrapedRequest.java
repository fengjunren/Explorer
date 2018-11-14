package cn.explo.config.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

public class WrapedRequest extends HttpServletRequestWrapper {

	private byte[] requestBody = null;
	private Map<String, String[]> parameterMap; // 所有参数的Map集合

	public WrapedRequest(HttpServletRequest request) {

		super(request);
		parameterMap = request.getParameterMap();

		// 缓存请求body
		try {
			requestBody = StreamUtils.copyToByteArray(request.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(byte[] _requestBody) {
		this.requestBody = _requestBody;
	}
	
	public Map<String, String[]> getParamMap () {
		return this.parameterMap;
	}
	public void setParamMap (Map<String, String[]> _parameterMap) {
		this.parameterMap = _parameterMap;
	}

	/**
	 * 重写 getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (requestBody == null) {
			requestBody = new byte[0];
		}
		return new WrapedInputStream(requestBody);
	}

	/**
	 * 重写 getReader()
	 */
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	/**
	 * 获取所有参数名
	 *
	 * @return 返回所有参数名
	 */
	@Override
	public Enumeration<String> getParameterNames() {
		Vector<String> vector = new Vector<String>(parameterMap.keySet());
		return vector.elements();
	}

	/**
	 * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
	 *
	 * @param name 指定参数名
	 * @return 指定参数名的值
	 */
	@Override
	public String getParameter(String name) {
		String[] results = parameterMap.get(name);
        return results[0];
	}

	/**
	 * 获取指定参数名的所有值的数组，如：checkbox的所有数据 接收数组变量 ，如checkobx类型
	 */
	@Override
	public String[] getParameterValues(String name) {
		return parameterMap.get(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}

	private static class WrapedInputStream extends ServletInputStream {

		private final ByteArrayInputStream inputStream;

		public WrapedInputStream(byte[] bytes) {
			inputStream = new ByteArrayInputStream(bytes);
		}

		@Override
		public int read() throws IOException {
			return inputStream.read();
		}

		@Override
		public boolean isFinished() {
			return inputStream.available() == 0;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener readlistener) {
		}

	}
}
