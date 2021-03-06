package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.Bus;
import com.sf.service.BusService;

/**
 * @author Amitabh Tiwari
 *
 */
@RestController
@RequestMapping(path = "/v1/buses")
public class BusController {

	@Autowired
	private BusService busService;

	@PostMapping()
	public void createBusRecord(@RequestBody Bus bus) {
		busService.saveEntry(bus);
	}

	@GetMapping(path = "/{id}")
	public Bus getBusRecord(@PathVariable int id) {
		return busService.getRecord(id);
	}

	@GetMapping()
	public Iterable<Bus> getDrivers() {
		return busService.getRecords();
	}

}
