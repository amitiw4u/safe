package com.sf.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


@Configuration
@Component
public class RedisConfig {

	@Value("${redis.hostname}")
	private String hostname;
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		RedisStandaloneConfiguration rsc = jedisConnectionFactory.getStandaloneConfiguration();
		rsc.setHostName(hostname);
		rsc.setPort(RedisConstants.REDIS_PORT);
	    return jedisConnectionFactory;
	}

	@Bean(name = "REDIS_AUTH_TOKEN_DATABASE")
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		JedisConnectionFactory jedisConnectionFactory = jedisConnectionFactory();
		jedisConnectionFactory.getStandaloneConfiguration().setDatabase(0);
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
