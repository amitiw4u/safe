package com.sf.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sf.bean.Trip;

@Repository
public interface TripDao extends PagingAndSortingRepository<Trip, Integer> {
	
	@Query(value = "SELECT * FROM smartBus_trip as t WHERE t.driverId=:driverId"
			+ " AND curdate() BETWEEN date_format(t.startTime, '%Y-%m-%d') AND date_format(t.endTime, '%Y-%m-%d')"
			+ " AND CURRENT_TIME BETWEEN t.departureTime AND t.arrivalTime ORDER BY t.departureTime ASC LIMIT 1", nativeQuery = true)
	List<Map<String, Object>> getTripForDriverToday(@Param("driverId") int driverId);
	
	@Query(value = "SELECT * FROM smartBus_trip as t WHERE t.conductorId=:conductorId"
			+ " AND curdate() BETWEEN date_format(t.startTime, '%Y-%m-%d') AND date_format(t.endTime, '%Y-%m-%d')"
			+" AND CURRENT_TIME BETWEEN t.departureTime AND t.arrivalTime ORDER BY t.departureTime ASC LIMIT 1", nativeQuery = true)
	List<Map<String, Object>> getTripForConductorToday(@Param("conductorId") int conductorId);

	@Query(value="SELECT * FROM smartSchoolBus.smartBus_trip as t" + 
			" WHERE current_time() + interval :minutes minute BETWEEN t.departureTime AND t.arrivalTime" + 
			" AND curdate() BETWEEN date_format(t.startTime, '%Y-%m-%d') AND date_format(t.endTime, '%Y-%m-%d')"+
			" ORDER BY t.departureTime", nativeQuery = true)
	List<Map<String, Object>> getTodayPlannedStartTrip(@Param("minutes") int minutes);

}
