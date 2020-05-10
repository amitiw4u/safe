package com.sf.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sf.bean.Notification;

@Repository
public interface NotificationDao extends PagingAndSortingRepository<Notification, Integer>{
	
	@Query(value="SELECT * FROM smartbus_notification WHERE studentId=:studentId AND tripId=:tripId AND current_date() = sentAt",nativeQuery = true)
	public Notification getNotificationByTripStudentForCurrentDate(@Param("studentId") int studentId, @Param("tripId") int tripId);

}
