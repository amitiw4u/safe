package com.sf.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "smartBus_trip")
@Data
@ToString
public class Trip implements Serializable{

	public Trip() {
		
	}
	
	public Trip(int id, int routeId, Date departureTime, Date arrivalTime, String direction, int operationalId, Date startTime,
			int driverId, int conductorId, Date endTime, double distance) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.routeId = routeId;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.direction = direction;
		this.operationalId = operationalId;
		this.startTime = startTime;
		this.driverId = driverId;
		this.conductorId = conductorId;
		this.endTime = endTime;
		this.distance = distance;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "routeId")
	private int routeId;
	
	@Column(name = "distance")
	private double distance;

	@Column(name = "departureTime")
	private Date departureTime;

	@Column(name = "arrivalTime")
	private Date arrivalTime;

	@Column(name = "direction")
	private String direction;
	
	@Column(name = "operationalId")
	private int operationalId;

	@Column(name = "startTime")
	private Date startTime;

	@Column(name = "driverId")
	private int driverId;

	@Column(name = "conductorId")
	private int conductorId;

	@Column(name = "endTime")
	private Date endTime;

}
