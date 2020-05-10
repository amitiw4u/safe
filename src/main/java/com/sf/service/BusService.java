package com.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.Bus;
import com.sf.dao.BusDao;

@Service
public class BusService {

	@Autowired
	private BusDao busDao;

	public void saveEntry(Bus bus) {
		busDao.save(bus);
	}

	public Bus getRecord(int id) {
		return busDao.findById(id).get();
	}

	public Iterable<Bus> getRecords() {
		return busDao.findAll();
	}

}
