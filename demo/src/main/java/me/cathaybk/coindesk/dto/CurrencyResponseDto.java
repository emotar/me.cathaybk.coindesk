package me.cathaybk.coindesk.dto;

public class CurrencyResponseDto {
	
	private String code;
	
	private String chineseName;

	public CurrencyResponseDto() {}
	
	public CurrencyResponseDto(String code, String chineseName) {
		this.code = code;
		this.chineseName = chineseName;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	
}
