package me.cathaybk.coindesk.dto;

public class ResponseDto<T> {
	
	public static final String SUCCESS_CODE = "0000";
	
	public static final String ERROR_CODE = "1111";
	
	private T payload;
	
	private String code;
	
	private String message;

	private ResponseDto(T payload, String code, String message) {
		this.code = code;
		this.payload = payload;
		this.message = message;
	}
	
	public static <T> ResponseDto<?> success(T t) {
		return new ResponseDto<T>(t, ResponseDto.SUCCESS_CODE, "操作成功");
	}
	
	public static <T> ResponseDto<?> success() {
		return new ResponseDto<T>(null, ResponseDto.SUCCESS_CODE, "操作成功");
	}	
	
	public static <T> ResponseDto<?> error(T t, String message) {
		return new ResponseDto<T>(t, ResponseDto.ERROR_CODE, message);
	}	
	
	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
