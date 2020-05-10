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
@Table(name = "smartBus_route")
@Data
@ToString
public class Route implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "direction")
	private String direction;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "schoolId")
	private int schoolId;
	
	@Column(name = "flag")
	private int flag;

	@Column(name = "geoFenceStartLat")
	private String geoFenceStartLat;

	@Column(name = "geoFenceEndLat")
	private String geoFenceEndLat;


	
	
}
