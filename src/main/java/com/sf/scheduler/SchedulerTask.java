package com.sf.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sf.bean.Notification;
import com.sf.bean.Student;
import com.sf.service.GPSService;
import com.sf.service.NotificationService;
import com.sf.service.StudentService;
import com.sf.service.TripService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SchedulerTask {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private GPSService gpsService;
	
	@Autowired
	private NotificationService notifyService;

	@Scheduled(fixedRate = 2000)
	public void scheduleTaskWithFixedRate() {
		
		List<Map<String, Object>> activeTrips = tripService.getTodayPlannedStartTrip(5);
		
		if (activeTrips.size() != 0) {
			for(int j=0;j<activeTrips.size();j++) {
				Map<String, Object> tripMap = (Map<String, Object>) activeTrips.get(j);
				int tripId = (int)tripMap.get("id");

				List<Map<String, Object>> studentTripMapList = studentService.getStudentMappingByTripId(tripId);
				System.out.println("Students Identified: "+studentTripMapList.size());
				for (int i=0;i<studentTripMapList.size();i++) {
					Map<String, Object> studentTripMap = studentTripMapList.get(i);
					int studentId = (int)studentTripMap.get("loginId");
					Student student = studentService.findObjectById(studentId);
					System.out.println("Student Identified:"+student.getName()+":"+student.getPickupScheduleId()+":"+student.getStudentGeoData());
					//student Id, trip Id, student Stop Id, student stop Geo Data

					Map<String, Object> pickupTime = studentService.getPickupTimeByStudentandTripId(studentId,tripId);
					Map<String, Object> dropTime = studentService.getDropTimeByStudentandTripId(studentId,tripId);
			        Calendar cal = Calendar.getInstance();
			        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			        
			        String currentTime = sdf.format(cal.getTime());
					
					long timeDifference = 0;
					
					if (pickupTime.get("arrivalTime") != null) {
						try {
							timeDifference = compareTwoTimeStamps((Time)pickupTime.get("arrivalTime"),new Time(sdf.parse(currentTime).getTime()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Pickup: "+Math.abs(timeDifference));
					}if (dropTime.get("arrivalTime") != null) {
						try {
							timeDifference = compareTwoTimeStamps((Time)dropTime.get("arrivalTime"),new Time(sdf.parse(currentTime).getTime()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Drop: "+Math.abs(timeDifference));
					}

					if(Math.abs(timeDifference) > 10) {
						System.out.println("Time Difference is greater than 10 minutes..");
						continue;
					}else {
						//check whether notification has been sent to this student before
						Notification notification = notifyService.getNotificationByStudentAndTripForToday(studentId, tripId);
						if(notification == null) {
							//update notification table
							Notification note = new Notification("Bus is coming to your stop","Text","sentTo","sentBy",
									new Date(),tripId,studentId);
							notifyService.saveEntry(note);
						}else {
							continue;
						}
					}
				}
			}
		}
	}

	private long compareTwoTimeStamps(java.sql.Time currentTime, java.sql.Time oldTime)
	{
	  long milliseconds1 = oldTime.getTime();
	  long milliseconds2 = currentTime.getTime();

	  long diff = milliseconds2 - milliseconds1;
	  long diffMinutes = diff / (60 * 1000);
	  return diffMinutes;
	}
}
