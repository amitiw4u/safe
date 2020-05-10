package com.sf.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sf.bean.StudentMapping;

@Repository
public interface StudentMappingDao extends PagingAndSortingRepository<StudentMapping, Integer>{
	
	@Query(value = "SELECT smp.tripId FROM smartSchoolBus.smartBus_StudentMapping as smp"
			+" WHERE smp.loginId=:loginId", nativeQuery = true)
	List<Map<String, Object>> findAllByLoginId(@Param("loginId")int loginId);

}
