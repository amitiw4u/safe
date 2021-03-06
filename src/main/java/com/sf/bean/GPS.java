package com.sf.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "smartBus_GPS")
@Data
@ToString
public class GPS implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "GPGGA")
	private String gpgga;
	
	@Column(name = "deviceId")
	private String deviceId;
	
	@Column(name = "busId")
	private int busId;
	
	@Column(name = "timestamp")
	private Date timestamp;

}
