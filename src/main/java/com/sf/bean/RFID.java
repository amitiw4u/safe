package com.sf.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "smartBus_RFID")
@Data
@ToString
public class RFID implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "deviceId")
	private String deviceId;
	
	@Column(name = "cardId")
	private String cardId;
	
	@Column(name = "busId")
	private int busId;
	
	@Column(name = "timeStamp")
	private Date timeStamp;
	
	@Column(name = "status")
	private int status;

}
