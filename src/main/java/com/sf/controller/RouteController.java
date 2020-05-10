package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.Route;
import com.sf.service.RouteService;

/**
 * @author Amitabh Tiwari
 *
 */
@RestController
@RequestMapping(path = "/v1/rotutes")
public class RouteController {

	@Autowired
	private RouteService routeService;

	@PostMapping()
	public void createRouteRecord(@RequestBody Route route) {
		routeService.saveEntry(route);
	}

	@GetMapping(path = "/{id}")
	public Route getRouteRecord(@PathVariable int id) {
		return routeService.getRecord(id);
	}

	@GetMapping()
	public Iterable<Route> getRoutes() {
		return routeService.getRecords();
	}

}
