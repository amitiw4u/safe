package com.sf.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sf.bean.GPS;
import com.sf.dao.GPSDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GPSService {
	
	@Autowired
	private GPSDao gpsDaoV3;
	
	public GPS getRecord(int id) {
		return gpsDaoV3.findById(id).get();
	}
	
	public GPS saveEntry(GPS gps) {
		GPS managedGPSV3;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		gps.setTimestamp(timestamp);
		managedGPSV3 = gpsDaoV3.save(gps);
		return managedGPSV3;
	}
}
