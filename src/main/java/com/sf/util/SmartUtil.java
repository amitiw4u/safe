package com.sf.util;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmartUtil {

	public static final String EMAIL_REGEX =
	                "^[_A-Za-z0-9-]+([\\.]{1}[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static boolean validateEmail(String email) {
		Pattern emailNamePtrn = Pattern.compile(EMAIL_REGEX);
		Matcher mtch = emailNamePtrn.matcher(email);
		if (mtch.matches()) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertObjectToMap(Object valueType) {
		return objectMapper.convertValue(valueType, Map.class);
	}

	public static boolean validateUserEmail(String user) {
		Predicate<String> isValidEmail = u -> !StringUtils.isEmpty(u) && SmartUtil.validateEmail(u);
		return isValidEmail.apply(user);
	}

	/**
	 * This method is used for formating input string with params
	 * 
	 * @param input
	 * @param params
	 * @return
	 */
	public static String formatString(String input, Object... params) {
		MessageFormat fmt = new MessageFormat(input);
		return fmt.format(params);
	}
	
	public static String generateAuthToken(List<String> keys) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int bound = 100000;
		for (String key : keys) {
			sb.append(random.nextInt(bound));
			sb.append(key);
			sb.append(random.nextInt(bound));
		}
		Calendar cal = Calendar.getInstance();
		sb.append(cal.getTime().getTime());
		String authToken = Crypto.encrypt(sb.toString());
		log.info("Genertaed authToken:{}", authToken);
		authToken = authToken.replaceAll("[/+]+", "");
		return authToken;
	}
}
