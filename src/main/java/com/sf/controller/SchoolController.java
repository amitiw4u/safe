package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.Route;
import com.sf.bean.School;
import com.sf.service.SchoolService;

/**
 * @author Amitabh Tiwari
 *
 */
@RestController
@RequestMapping(path = "/v1/schools")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	@PostMapping()
	public void createSchoolRecord(@RequestBody School school) {
		schoolService.saveEntry(school);
	}

	@GetMapping(path = "/{id}")
	public School getSchoolRecord(@PathVariable int id) {
		return schoolService.getRecord(id);
	}

	@GetMapping()
	public Iterable<School> getSchools() {
		return schoolService.getRecords();
	}

}
