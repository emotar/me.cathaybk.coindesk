package me.cathaybk.coindesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.openjpa.persistence.jdbc.Unique;

@Entity
@Table(name = "Currency")
public class CurrencyEntity extends CommonEntity {

	private static final long serialVersionUID = 1L;

	@Unique
	@Column(nullable = false, unique = true, updatable = false, length = 20)
	private String code;
	
	
	@Column(name = "chinese_name", nullable = false, length = 80)
	private String chineseName;
	

	public CurrencyEntity() {
		
	}
	
	public CurrencyEntity(String code, String chineseName) {
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
