package cn.explo.utils;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.Async;

public class CommonUtil {
	@Async
	public static void write(HttpServletResponse response, String msg) throws IOException {
		ServletOutputStream sos = response.getOutputStream();
		sos.write(msg.getBytes());
		sos.close();
	}
}
