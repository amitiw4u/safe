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
@Table(name = "smartBus_schedule")
@Data
@ToString
public class Schedule implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "stopGeoData")
	private String stopGeoData;
	
	@Column(name = "stopName")
	private String stopName;
	
	@Column(name = "tripId")
	private int tripId;
	
	@Column(name = "arrivalTime")
	private Date arrivalTime;

}
