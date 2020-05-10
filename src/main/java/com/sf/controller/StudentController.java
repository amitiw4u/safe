package com.sf.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.Student;
import com.sf.service.StudentService;

/**
 * @author Amitabh Tiwari
 *
 */
@RestController
@RequestMapping(path = "/v1/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping()
	public void createStudentRecord(@RequestBody Student student) {
		studentService.saveEntry(student);
	}

	@GetMapping(path = "/{id}")
	public List<Map<String, Object>> findStudentCurrentDetailsById(@PathVariable(name="id") int id) {
		return null;
	}	

	@GetMapping()
	public Iterable<Student> getDrivers() {
		return studentService.getRecords();
	}

	@GetMapping(path="/user/{username}")
	public List<Map<String, Object>> findStudentCurrentDetailsByParentUsername(@PathVariable(name="username") String username) {
		return null;
	}

}
