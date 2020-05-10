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
@Table(name = "smartBus_students")
@Data
@ToString
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "gender")
	private int gender;

	@Column(name = "age")
	private int age;

	@Column(name = "dob")
	private String dob;

	@Column(name = "class")
	private String className;

	@Column(name = "schoolId")
	private int schoolId;

	@Column(name = "parentId")
	private int parentId;

	@Column(name = "startTime")
	private Date startTime;
	
	@Column(name = "endTime")
	private Date endTime;
	
	@Column(name = "imgUrl")
	private String imgUrl;
	
	@Column(name = "cardId")
	private String cardId;
	
	@Column(name = "studentGeoData")
	private String studentGeoData;
	
	@Column(name = "pickupScheduleId")
	private int pickupScheduleId;
	
	@Column(name = "dropScheduleId")
	private int dropScheduleId;
	
}

