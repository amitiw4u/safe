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
@Table(name = "smartBus_drivers")
@Data
@ToString
public class Driver implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;

	@Column(name = "type")
	private int type;

	@Column(name = "gender")
	private int gender;
	
	@Column(name = "licenseNo")
	private String licenseNo;
	
	@Column(name = "isActive")
	private boolean isActive;

	@Column(name = "cardId")
	private String cardId;
	
	@Column(name = "nationalId")
	private String nationalId;

	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	@Column(name = "distanceTraveled")
	private double distanceTraveled;
	
	@Column(name = "licenseCategory")
	private String licenseCategory;
	
	@Column(name = "expiryDate")
	private String expiryDate;
}
