package com.sf.validator;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sf.bean.AuthResponse;
import com.sf.cache.CacheDao;
import com.sf.cache.RedisConstants;
import com.sf.config.Constants;
import com.sf.error.ApplicationErrorMessage;
import com.sf.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private CacheDao cache;

	private Map<String, Boolean> methodAuthenticationMap = new WeakHashMap<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Method mappingMethod = ((HandlerMethod) handler).getMethod();
		boolean ifExsist = methodAuthenticationMap.containsKey(mappingMethod.toString());
		boolean skipValidation = ifExsist ? methodAuthenticationMap.get(mappingMethod.toString()) : false;
		if (skipValidation == true) {
			return true;
		}
		if (skipValidation == false) {
			NoAuthValidation skipAuthentication = mappingMethod.getAnnotation(NoAuthValidation.class);
			if (skipAuthentication != null) {
				methodAuthenticationMap.put(mappingMethod.toString(), true);
				return true;
			}
			methodAuthenticationMap.put(mappingMethod.toString(), false);
		}
		validateRequestHeaders(request);
		return true;
	}

	private void validateRequestHeaders(HttpServletRequest httpRequest) {
		String authToken = httpRequest.getHeader(Constants.AUTH_TOKEN_HEADER_PARAM);
		if (StringUtils.isEmpty(authToken)) {
			log.debug("AuthToken: {}", authToken);
			throw new BusinessException(ApplicationErrorMessage.MISSING_SESSION_TOKEN.getBusinessErrorMessage());
		}
		AuthResponse authResponse = cache.getDataValueFromRedis(authToken, AuthResponse.class,
				RedisConstants.REDIS_AUTH_TOKEN_DATABASE);
		if (authResponse == null) {
			log.debug("SessionToken: {}", authToken);
			throw new BusinessException(ApplicationErrorMessage.MISSING_SESSION_TOKEN.getBusinessErrorMessage(),
					HttpStatus.UNAUTHORIZED);
		}
		if (!StringUtils.equals(authToken, authResponse.getSessionToken())) {
			log.debug("SessionToken: {}", authToken);
			throw new BusinessException(ApplicationErrorMessage.INVALID_SESSION_TOKEN.getBusinessErrorMessage(),
					HttpStatus.UNAUTHORIZED);
		}
	}
}
