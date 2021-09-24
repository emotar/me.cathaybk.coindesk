package me.cathaybk.coindesk.exception;

public class BusinessException extends RuntimeException {
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
