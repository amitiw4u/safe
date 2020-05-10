package com.sf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.Route;
import com.sf.dao.RouteDao;

@Service
public class RouteService {

	@Autowired
	private RouteDao routeDao;

	public void saveEntry(Route route) {
		routeDao.save(route);
	}

	public Route getRecord(int id) {
		return routeDao.findById(id).get();
	}

	public Iterable<Route> getRecords() {
		return routeDao.findAll();
	}

}
