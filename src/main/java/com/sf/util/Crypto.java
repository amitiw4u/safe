package com.sf.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sf.config.Constants;




/**
 * To Encrypt and Decrypt the given string using key.
 * 
 * @author Lenskart
 *
 */

public class Crypto {

	private static SecretKeySpec s_key;

	private static SecretKeySpec getKey() {
		if (s_key == null) {
			try {
				byte[] rawArray = Base64.getDecoder().decode("0UjOm5tA4SwKVpAJbUyTOw==");
				s_key = new SecretKeySpec(rawArray, "AES");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return s_key;
	}

	public static String encrypt(String a_plainText) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, getKey());

			byte[] plainArray = a_plainText.getBytes(Constants.URL_ENCODING_UTF8);
			byte[] cipherArray = cipher.doFinal(plainArray);

			String result = Base64.getEncoder().encodeToString(cipherArray);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unused")
	private static String encrypt(Hashtable<String, String> a_paramMap) {
		try {
			String plainText = "";
			Enumeration<String> keyEnum = a_paramMap.keys();
			while (keyEnum.hasMoreElements()) {
				String key = keyEnum.nextElement();
				if (!plainText.isEmpty()) {
					plainText += "&";
				}
				plainText += key + "=" + URLEncoder.encode(a_paramMap.get(key), Constants.URL_ENCODING_UTF8);
			}

			String cipherText = encrypt(plainText);
			return URLEncoder.encode(cipherText, Constants.URL_ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unused")
	private static String decryptString(String a_cipherText) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, getKey());
			byte[] cipherArray = Base64.getDecoder().decode(a_cipherText);
			byte[] plainArray = cipher.doFinal(cipherArray);

			String plainText = new String(plainArray);
			return plainText;
		} catch (Exception e) {
		}
		return a_cipherText;
	}

	@SuppressWarnings("unused")
	private static String decryptForURL(String a_cipherText) {
		try {
			String decodeText = URLDecoder.decode(a_cipherText, Constants.URL_ENCODING_UTF8);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, getKey());
			byte[] cipherArray = Base64.getDecoder().decode(decodeText);
			byte[] plainArray = cipher.doFinal(cipherArray);

			String plainText = new String(plainArray);
			return plainText;
		} catch (Exception e) {
		}
		return a_cipherText;
	}

	@SuppressWarnings("unused")
	private static String decrypt(String a_cipherText, String a_aesKey) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, getAesKey(a_aesKey));
			byte[] cipherArray = Base64.getDecoder().decode(a_cipherText);
			byte[] plainArray = cipher.doFinal(cipherArray);

			String result = new String(plainArray);
			return result;
		} catch (Exception e) {
		}
		return null;
	}

	private static SecretKeySpec getAesKey(String a_aesKey) {
		try {
			byte keyArray[] = Base64.getDecoder().decode(a_aesKey);
			return new SecretKeySpec(keyArray, "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static String encrypt(String a_plainText, String a_aesKey) {
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, getAesKey(a_aesKey));
			byte[] plainArray = a_plainText.getBytes(Constants.URL_ENCODING_UTF8);
			byte[] cipherArray = cipher.doFinal(plainArray);

			String result = Base64.getEncoder().encodeToString(cipherArray);
			;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
