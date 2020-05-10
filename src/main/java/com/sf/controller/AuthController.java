package com.sf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.bean.AuthRequest;
import com.sf.bean.AuthResponse;
import com.sf.service.AuthService;
import com.sf.validator.NoAuthValidation;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping
	@NoAuthValidation
	public AuthResponse authUser(@RequestBody AuthRequest authRequest)
	{
		return authService.authenticate(authRequest);
	}
	
}
