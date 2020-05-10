package com.sf.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sf.bean.TripMap;

@Repository
public interface TripMapDao extends PagingAndSortingRepository<TripMap, Integer> {
	
	@Query(value = "SELECT tripMap.id, tripMap.started,tripMap.tripId,tripMap.conductorId FROM "
			+ "smartSchoolBus.smartBus_TripMapping as tripMap"
			+ " WHERE tripMap.driverId=:driverId AND tripMap.busId=:busId AND tripMap.started = 1"
			+ " AND date_format(tripMap.timestamp, '%Y-%m-%d') = curdate() ", nativeQuery = true)
	List<Map<String, Object>> getOpenTripForDriver(@Param("driverId") int driverId, @Param("busId") int busId);
	
	@Query(value = "SELECT * FROM smartSchoolBus.smartBus_TripMapping as tripMap WHERE tripMap.id=:id", nativeQuery = true)
	TripMap findObjectById(@Param("id") int id);

	@Query(value = "SELECT tripMap.id, tripMap.driverId, tripMap.conductorId, tripMap.tripId FROM "
			+ "smartSchoolBus.smartBus_TripMapping as tripMap"
			+ " WHERE tripMap.busId=:busId AND tripMap.started = 1"
			+ " AND date_format(tripMap.timestamp, '%Y-%m-%d') = curdate() ", nativeQuery = true)
	List<Map<String, Object>> getOpenTripForStudent(@Param("busId") int busId);
	
	
	@Query(value = "SELECT tripMap.id, tripMap.driverId, tripMap.conductorId, tripMap.tripId, tripMap.started "
			+ "FROM smartSchoolBus.smartBus_TripMapping as tripMap"
			+ " WHERE tripMap.busId=:busId AND tripMap.started = 1"
			+ " AND date_format(tripMap.timestamp, '%Y-%m-%d') = curdate() ", nativeQuery = true)
	List<Map<String, Object>> getOpenTripForBus(@Param("busId") int busId);
	
	@Query(value = "SELECT * FROM smartSchoolBus.smartBus_TripMapping as tripMap"
			+ " WHERE tripMap.tripId=:tripId AND tripMap.busId=:busId AND tripMap.started = 1 AND tripMap.conductorId=:conductorId"
			+ " AND date_format(tripMap.timestamp, '%Y-%m-%d') = curdate() ", nativeQuery = true)
	List<Map<String, Object>> getOpenTripForConductor(@Param("tripId") int tripId, @Param("busId") int busId, @Param("conductorId") int conductorId);

	@Query(value = "SELECT tripMap.id, tripMap.driverId, tripMap.conductorId, tripMap.tripId FROM smartSchoolBus.smartBus_TripMapping as tripMap"
			+ " WHERE tripMap.busId=:busId AND tripMap.started = 1 AND tripMap.tripId=:tripId"
			+ " AND date_format(tripMap.timestamp, '%Y-%m-%d') = curdate() ", nativeQuery = true)
	List<Map<String, Object>> getOpenTripForStudent(@Param("busId") int busId,@Param("tripId") int tripId);

}
