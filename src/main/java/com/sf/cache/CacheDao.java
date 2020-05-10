package com.sf.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheDao {

	@Autowired
	@Qualifier("REDIS_AUTH_TOKEN_DATABASE")
	private RedisTemplate<String, Object> redisAuthTokenDatabase;

	public boolean setDataValueToRedis(String key, Object object, int expTime, int database) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(database);
		redisTemplate.opsForValue().set(key, object, expTime, TimeUnit.SECONDS);
		return true;
	}

	public RedisTemplate<String, Object> getRedisTemplate(int database) {
		return redisAuthTokenDatabase;
	}

	public boolean setDataValueToRedis(String key, Object object, int expTime) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(0);
		redisTemplate.opsForValue().set(key, object, expTime, TimeUnit.SECONDS);
		return true;
	}

	public <T> T getDataValueFromRedis(String key, Class<T> t) {
		return getDataValueFromRedis(key, t, 0);
	}

	public <T> T getDataValueFromRedis(String key, Class<T> t, int database) {
		RedisTemplate<?, ?> redisTemplate = getRedisTemplate(database);
		Object object = redisTemplate.opsForValue().get(key);
		if (object == null || !(t.isAssignableFrom(object.getClass()))) {
			return null;
		}

		@SuppressWarnings("unchecked")
		T tObject = (T) object;
		return tObject;
	}

	public boolean deleteKeyFromRedis(String key) {
		return deleteKeyFromRedis(key, 0);
	}

	public boolean deleteKeyFromRedis(String key, int database) {
		RedisTemplate<String, ?> redisTemplate = getRedisTemplate(database);
		redisTemplate.delete(key);
		return true;
	}

	public boolean setDataListToRedis(String key, List<Object> object, int expTime) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(0);
		redisTemplate.expire(key, expTime, TimeUnit.SECONDS);
		redisTemplate.opsForList().leftPushAll(key, object);
		return true;
	}

	public List<Object> getDataListFromRedis(String key) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(0);
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	public boolean setDataValueIfNotExists(String key, int database, Object value, int expTime) {
		RedisTemplate<String, Object> redisTemplate = getRedisTemplate(database);
		if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
			return redisTemplate.expire(key, expTime, TimeUnit.SECONDS);
		}
		return false;
	}

}
