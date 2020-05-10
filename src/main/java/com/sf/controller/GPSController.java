package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.GPS;
import com.sf.response.SuccessResponse;
import com.sf.service.GPSService;
import com.sf.validator.NoAuthValidation;

@RestController
@RequestMapping(path="/v3/gps")
public class GPSController {
	
	@Autowired
	private GPSService gpsService;
	
	@PostMapping()
	public SuccessResponse createGpsRecord(@RequestBody GPS gps)
	{
		SuccessResponse response = new SuccessResponse();
		gpsService.saveEntry(gps);
		response.setContent(gps);
		return response;
	}
	
	@GetMapping(path="/{id}")
	public GPS getRfidRecord(@PathVariable int id)
	{
		return gpsService.getRecord(id);
	}

}
