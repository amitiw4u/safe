package com.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.Notification;
import com.sf.bean.Route;
import com.sf.dao.NotificationDao;
import com.sf.dao.StudentDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService {
	
	@Autowired
	private NotificationDao notificationDao;
	
	public Notification getNotificationByStudentAndTripForToday(int studentId, int tripId){
		return notificationDao.getNotificationByTripStudentForCurrentDate(studentId, tripId);
	}
	
	public void saveEntry(Notification note) {
		notificationDao.save(note);
	}

}
