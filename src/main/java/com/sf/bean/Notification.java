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
@Table(name = "smartbus_notification")
@Data
@ToString
public class Notification implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "messageType")
	private String messageType;
	
	@Column(name = "sentTo")
	private String sentTo;
	
	@Column(name = "sentBy")
	private String sentBy;
	
	@Column(name = "sentAt")
	private Date sentAt;
	
	@Column(name = "tripId")
	private int tripId;
	
	@Column(name = "studentId")
	private int studentId;

	public Notification(String message, String messageType, String sentTo, String sentBy, Date sentAt, int tripId,
			int studentId) {
		super();
		this.message = message;
		this.messageType = messageType;
		this.sentTo = sentTo;
		this.sentBy = sentBy;
		this.sentAt = sentAt;
		this.tripId = tripId;
		this.studentId = studentId;
	}
	
	public Notification() {
		
	}

}
