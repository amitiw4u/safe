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
@Table(name = "smartBus_TripMapping")
@Data
@ToString
public class TripMap implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tripId")
	private int tripId;
	
	@Column(name = "busId")
	private int busId;
	
	@Column(name = "driverId")
	private int driverId;
	
	@Column(name = "conductorId")
	private int conductorId;
	
	@Column(name = "timestamp")
	private Date timestamp;
	
	@Column(name = "started")
	private int started;
	
	
	public TripMap() {
		
	}
	
	public TripMap(int tripId, int busId, int conductorId, int driverId, Date timestamp, int started) {
		super();
		this.tripId = tripId;
		this.busId = busId;
		this.conductorId = conductorId;
		this.driverId = driverId;
		this.timestamp = timestamp;
		this.started = started;
	}

}
