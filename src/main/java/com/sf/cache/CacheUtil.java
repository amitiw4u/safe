package com.sf.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Jaswant
 */
@Component
public class CacheUtil {

	@Autowired
	private CacheDao cacheDao;

	@Autowired
	public Environment environment;

	/**
	 * This function fetches value from Cache of class type T Returns null if key is
	 * not found in Cache.</br>
	 * 
	 * @param <T>
	 *            -- Class Type for object to be fetched.
	 * @param key
	 *            -- Key to be fetched from Cache
	 * @param t
	 *            -- class Type
	 * @return -- <T> object of Type t
	 */
	public <T> T getObjectFromCache(String key, int database, Class<T> t) {
		return cacheDao.getDataValueFromRedis(key, t, database);
	}

	public <T> T getObjectFromCache(String key, Class<T> t) {
		return cacheDao.getDataValueFromRedis(key, t);
	}

	/**
	 * Sets an object in Cache.
	 *
	 * @param memCacheKey
	 *            -- key with which data should be saved in Cache
	 * @param obj
	 *            -- obj to be saved in Cache
	 * @param propertyName
	 *            -- propertyName with which expiration time should be fetched from
	 *            properties file
	 */
	public void setObjectInCache(String memCacheKey, Object obj, String propertyName) {
		int expTime = environment.getProperty(propertyName, Integer.class);
		cacheDao.setDataValueToRedis(memCacheKey, obj, expTime);
	}

	/**
	 * Sets an object in Cache.
	 *
	 * @param memCacheKey
	 *            -- key with which data should be saved in Cache
	 * @param obj
	 *            -- obj to be saved in Cache
	 * @param expTime
	 *            -- expiry time in case property is not defined
	 */
	public void setObjectInCache(String memCacheKey, Object obj, int expTime) {
		cacheDao.setDataValueToRedis(memCacheKey, obj, expTime);
	}

	/**
	 * This function deletes value from Cache of class type T Returns null if key is
	 * not found in Cache.</br>
	 * 
	 * @param <T>
	 *            -- Class Type for object to be fetched.
	 * @param key
	 *            -- Key to be fetched from Cache
	 * @param t
	 *            -- class Type
	 * @return -- <T> object of Type t
	 */
	public boolean deleteObjectFromCache(String key) {

		Object object = cacheDao.getDataValueFromRedis(key, Object.class);
		if (object == null) {
			return false;
		}
		cacheDao.deleteKeyFromRedis(key);
		return true;
	}

	public void setObjectInCache(String memCacheKey, Object obj, int expTime, Integer dataBase) {
		cacheDao.setDataValueToRedis(memCacheKey, obj, expTime, dataBase);
	}

	public Boolean deleteObjectFromCache(String key, int database) {
		Object object = cacheDao.getDataValueFromRedis(key, Object.class, database);
		if (object == null) {
			return false;
		}
		cacheDao.deleteKeyFromRedis(key, database);
		return true;
	}

	public boolean setObjectIfNotExist(String key, Object obj, int database, String propertyName) {
		int expTime = environment.getProperty(propertyName, Integer.class);
		return cacheDao.setDataValueIfNotExists(key, database, obj, expTime);
	}
}
