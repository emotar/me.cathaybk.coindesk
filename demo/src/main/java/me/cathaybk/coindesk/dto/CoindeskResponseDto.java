package me.cathaybk.coindesk.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CoindeskResponseDto {


	@JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	
	private List<Currency> currencies;

	
	public CoindeskResponseDto(Date updateTime, Currency... currency) {
		this.updateTime = updateTime;		
		this.currencies = new ArrayList<>(Arrays.asList(currency));	
	}
	
	public CoindeskResponseDto(Date updateTime, List<Currency> currencies) {
		this.updateTime = updateTime;
		this.currencies = currencies;	
	}
	
	
	public static class Currency {
		private String currencyCode;
		private String chineseCurrencyName;
		private Double rate;
		
		public String getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		public String getChineseCurrencyName() {
			return chineseCurrencyName;
		}
		public void setChineseCurrencyName(String chineseCurrencyName) {
			this.chineseCurrencyName = chineseCurrencyName;
		}
		public Double getRate() {
			return rate;
		}
		public void setRate(Double rate) {
			this.rate = rate;
		}
	}




	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public List<Currency> getCurrencies() {
		return currencies;
	}


	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
}
