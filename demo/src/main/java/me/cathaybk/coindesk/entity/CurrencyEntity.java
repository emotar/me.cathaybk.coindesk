package me.cathaybk.coindesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Currency")
public class CurrencyEntity extends CommonEntity {

	private static final long serialVersionUID = 1L;

	private String code;
	
	@Column(name = "chinese_name")
	private String chineseName;
	


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
