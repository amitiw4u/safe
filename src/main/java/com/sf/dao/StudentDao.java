package com.sf.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sf.bean.Student;

@Repository
public interface StudentDao extends PagingAndSortingRepository<Student, Integer> {

	@Query(value = "SELECT s.*, r.loginId FROM smartSchoolBus.smartBus_StudentMapping as r"
			+" INNER JOIN smartSchoolBus.smartBus_students as s ON r.loginId = s.id"
			+" WHERE r.tripId=:tripId", nativeQuery = true)
	List<Map<String, Object>> getStudentsWithTripForToday(@Param("tripId") int tripId);

	@Query(value = "SELECT * FROM smartSchoolBus.smartBus_StudentMapping as r"
			+" WHERE r.loginId=:loginId AND CURRENT_TIME BETWEEN r.startTime AND "
			+ "r.endTime ORDER BY r.startTime ASC LIMIT 1", nativeQuery = true)
	List<Map<String, Object>> getStudentTripForToday(@Param("loginId") int loginId);
	
	@Query(value = "SELECT * FROM smartSchoolBus.smartBus_students as r WHERE r.id=:id", nativeQuery = true)
	Student findObjectById(@Param("id") int id);

	@Query(value = "SELECT * FROM smartSchoolBus.smartBus_StudentMapping as r"
			+" WHERE r.tripId=:tripId", nativeQuery = true)
	List<Map<String, Object>> getStudentMappingByTripId(@Param("tripId") int tripId);

	@Query(value = "SELECT sch1.arrivalTime FROM smartSchoolBus.smartBus_students as s " + 
			"INNER JOIN smartSchoolBus.smartBus_schedule as sch1 ON sch1.id = s.pickupScheduleId " + 
			"WHERE s.id=:id AND sch1.tripId=:tripId", nativeQuery = true)
	Map<String, Object> getPickupTimeByStudentandTripId(@Param("id") int id, @Param("tripId") int tripId);
	
	@Query(value = "SELECT sch2.arrivalTime FROM smartSchoolBus.smartBus_students as s "
			+ "INNER JOIN smartSchoolBus.smartBus_schedule as sch2 ON sch2.id = s.dropScheduleId"
			+" WHERE s.id=:id AND sch2.tripId=:tripId", nativeQuery = true)
	Map<String, Object> getDropTimeByStudentandTripId(@Param("id") int id, @Param("tripId") int tripId);

}
