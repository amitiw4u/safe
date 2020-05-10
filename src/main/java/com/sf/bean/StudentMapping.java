package com.sf.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "smartBus_StudentMapping")
@Data
@ToString
public class StudentMapping implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "startTime")
	private Date startTime;
	
	@Column(name = "endTime")
	private Date endTime;
	
	@Column(name = "tripId")
	private int tripId;
	
	@Column(name = "loginId")
	private int loginId;

}
