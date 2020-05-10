package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.Driver;
import com.sf.service.DriverService;


/**
 * @author Amitabh Tiwari
 *
 */
@RestController
@RequestMapping(path="/v1/drivers")
public class DriverController {
	
	@Autowired
	private DriverService driverService;
	
	
	@PostMapping()
	public void createDriverRecord(@RequestBody Driver driver)
	{
		driverService.saveEntry(driver);
	}
	
	@GetMapping(path="/{id}")
	public Driver getDriverRecord(@PathVariable int id)
	{
		return driverService.getRecord(id);
	}
	
	@GetMapping()
	public Iterable<Driver> getDrivers()
	{
		return driverService.getRecords();
	}

}
