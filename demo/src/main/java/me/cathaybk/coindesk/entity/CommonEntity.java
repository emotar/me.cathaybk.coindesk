package me.cathaybk.coindesk.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@MappedSuperclass
public class CommonEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_USER = "creator";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Integer id;
	
	private String creator;
	
	@Column(name = "create_time")
	@JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss", timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	
	@PrePersist
	public void setCreateInfo() {
		this.createTime = new Date();
		this.creator = CommonEntity.DEFAULT_USER;
	}
	

}
