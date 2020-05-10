package com.sf.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sf.bean.CardMapping;

@Repository
public interface CardMappingDao extends PagingAndSortingRepository<CardMapping, Integer>{
	
	@Query(value = "SELECT cmp.loginId, cmp.type FROM smartSchoolBus.smartBus_cardMapping as cmp"
			+" WHERE cmp.cardId=:cardId", nativeQuery = true)
	List<Map<String, Object>> findAllByCardId(@Param("cardId")String cardId);

}
