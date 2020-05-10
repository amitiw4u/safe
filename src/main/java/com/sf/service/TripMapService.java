package com.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sf.bean.TripMap;
import com.sf.dao.TripMapDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TripMapService {
	
	@Autowired
	private TripMapDao tripMapDao;
	
	public void saveEntry(TripMap parent)
	{
		tripMapDao.save(parent);
	}

	public TripMap getRecord(int id) {
		return tripMapDao.findById(id).get();
	}
	
	public Iterable<TripMap> getRecords() {
		return tripMapDao.findAll();
	}

}
