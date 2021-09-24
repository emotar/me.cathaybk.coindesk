package me.cathaybk.coindesk.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

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

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@PrePersist
	public void setCreateInfo() {
		this.createTime = new Date();
		this.creator = CommonEntity.DEFAULT_USER;
	}
	

}
