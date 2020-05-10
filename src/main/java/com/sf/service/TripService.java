package com.sf.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.Trip;
import com.sf.dao.TripDao;

@Service
public class TripService {

	@Autowired
	private TripDao tripDao;

	public void saveEntry(Trip trip) {
		tripDao.save(trip);
	}

	public Trip getRecord(int id) {
		return tripDao.findById(id).get();
	}
	
	public List<Map<String, Object>> getTodayPlannedStartTrip(int minute) {
		return tripDao.getTodayPlannedStartTrip(minute);
		
	}

	public Iterable<Trip> getRecords() {
		return tripDao.findAll();
	}

}
