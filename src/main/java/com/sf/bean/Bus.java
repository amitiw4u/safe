package com.sf.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "smartBus_buses")
@Data
@ToString
public class Bus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "registrationNo")
	private String registrationNo;

	@Column(name = "type")
	private int type;

	@Column(name = "operator")
	private String operator;

	@Column(name = "jioSimNo")
	private String jioSimNo;

	@Column(name = "jioDeviceNo")
	private boolean jioDeviceNo;

	@Column(name = "gpsDeviceNo")
	private int gpsDeviceNo;

	@Column(name = "cameraNo")
	private String cameraNo;

	@Column(name = "rfIdNo")
	private String rfIdNo;

	@Column(name = "raspberrypiNo")
	private String raspberrypiNo;
	
	@Column(name = "isActive")
	private boolean isActive;
	
}
