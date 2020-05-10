package com.sf.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sf.bean.RFID;

@Repository
public interface RFIDDao extends PagingAndSortingRepository<RFID, Integer>{
	
	@Query(value = "SELECT r.busId, r.status, r.timeStamp,r.id,r.cardId FROM smartSchoolBus.smartBus_RFID as r"
			+" WHERE r.cardId=:cardId AND date_format(r.timestamp, '%Y-%m-%d') = curdate()"
			+" ORDER BY r.timeStamp DESC LIMIT 1", nativeQuery = true)
	List<Map<String, Object>> getTodayCheckInData(@Param("cardId") String cardId);

}
