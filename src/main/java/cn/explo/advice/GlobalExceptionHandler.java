package cn.explo.advice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.explo.exception.GlobalException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public Map<String, String> globalExceptionHandler(HttpServletRequest req, GlobalException e) {
        
        Map<String, String> re = new HashMap<String, String>();
        re.put("error", e.errCode);
        re.put("msg", e.errMsg);
        return re;
    }

}
