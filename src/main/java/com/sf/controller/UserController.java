package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.User;
import com.sf.service.UserService;

/**
 * @author Amitabh Tiwari
 *
 */
@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping()
	public void createBusRecord(@RequestBody User parent) {
		userService.saveEntry(parent);
	}

	@GetMapping(path = "/{id}")
	public User getParentRecord(@PathVariable int id) {
		return userService.getRecord(id);
	}

	@GetMapping()
	public Iterable<User> getParents() {
		return userService.getRecords();
	}
}
