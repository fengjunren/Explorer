package cn.explo.exception;

public class GlobalException extends RuntimeException{
	public String errMsg;
	public String errCode;
	public GlobalException (String errCode,String errMsg) {
		this.errMsg = errMsg;
		this.errCode = errCode;
	}
}
