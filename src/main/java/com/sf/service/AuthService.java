package com.sf.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.bean.AuthRequest;
import com.sf.bean.AuthResponse;
import com.sf.bean.User;
import com.sf.cache.CacheUtil;
import com.sf.dao.AuthDao;
import com.sf.exception.SafeException;
import com.sf.util.SmartUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	@Autowired
	private AuthDao authDao;

	@Autowired
	private UserService userService;

	@Autowired
	private CacheUtil chacheUtil;

	public AuthResponse authenticate(AuthRequest authRequest) {
		try {
			if (authRequest.getIsEncodingRequired()) {
				String saltPasswordQuery = authDao.getPassWord(authRequest.getPassword());
				User user = userService.getUserAndPwd(authRequest.getUserName(), saltPasswordQuery);
				log.debug("Response: {} ", user.toString());
				return buildResponse(user);
			} else {
				User user = userService.getUserAndPwd(authRequest.getUserName(), authRequest.getPassword());
				log.debug("Response: {} ", user.toString());
				return buildResponse(user);
			}

		} catch (Exception e) {
			log.debug("The username: {} ", authRequest.getUserName());
			log.debug("The password: {} ", authRequest.getPassword());
			log.debug("The isEncodingRequired: {} ", authRequest.getIsEncodingRequired());
			throw new SafeException("Exception occured while getting user info:", e);
		}
	}

	private AuthResponse buildResponse(User user) {
		AuthResponse response = new AuthResponse();
		response.setEmail(user.getEmail());
		response.setName(user.getName());
		response.setUserName(user.getUsername());
		response.setType(user.getType());
		List<String> keys = new ArrayList<>();
		keys.add(user.getEmail());
		keys.add(user.getUsername());
		keys.add(String.valueOf(user.getType()));
		String authToken = SmartUtil.generateAuthToken(keys);
		response.setSessionToken(authToken);
		chacheUtil.setObjectInCache(authToken, response, 30 * 24 * 60 * 60);
		return response;
	}

}
