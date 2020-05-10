package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.RFID;
import com.sf.response.SuccessResponse;
import com.sf.service.RFIDService;
import com.sf.validator.NoAuthValidation;

@RestController
@RequestMapping(path="/v3/rfids")
public class RFIDController {
	
	@Autowired
	private RFIDService rfidService;
	
	@PostMapping()
	public SuccessResponse createRfidRecode(@RequestBody RFID rfid)
	{
		
		RFID returnRFID = rfidService.saveEntry(rfid);
		if (returnRFID.getStatus() == -1) {
			SuccessResponse response = new SuccessResponse();
			response.setContent(rfid);
			return response;
		}else if (returnRFID.getStatus() == 1) {
			SuccessResponse response = new SuccessResponse();
			response.setContent(returnRFID);
			return response;
		}else {
			SuccessResponse response = new SuccessResponse();
			response.setContent(returnRFID);
			return response;
		}

	}
	
	@GetMapping(path="/{id}")
	public RFID getRfidRecord(@PathVariable int id)
	{
		return rfidService.getRecord(id);
	}

}
