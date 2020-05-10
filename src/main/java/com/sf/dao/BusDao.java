package com.sf.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sf.bean.Bus;

@Repository
public interface BusDao extends CrudRepository<Bus, Integer> {

	@Query(value = "SELECT sb.* FROM smartSchoolBus.smartBus_StudentMapping sbm "
			+ "INNER JOIN smartSchoolBus.smartBus_TripMapping stm on stm.id=sbm.tripMappingId "
			+ "INNER JOIN  smartSchoolBus.smartBus_buses sb on stm.busId= sb.Id "
			+ "where sbm.studentId=:studentId and sb.isActive=1;", nativeQuery = true)
	Bus getBusDetailsByStudent(@Param("studentId") int studentId);

	@Query(value = "SELECT sbm.* FROM smartSchoolBus.smartBus_TripMapping sbm "
			+ "INNER JOIN  smartSchoolBus.smartBus_buses sb on sbm.busId= sb.Id "
			+ "where sbm.driverId=:driverId and sb.isActive=1;", nativeQuery = true)
	Bus getBusDetailsByDriver(@Param("driverId") int studentId);

}
