package com.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.Driver;
import com.sf.dao.DriverDao;

@Service
public class DriverService {

	@Autowired
	private DriverDao driverDao;
	
	public void saveEntry(Driver driver)
	{
		driverDao.save(driver);
	}

	public Driver getRecord(int id) {
		return driverDao.findById(id).get();
	}

	public Iterable<Driver> getRecords() {
		 return driverDao.findAll();
	}
	
}
