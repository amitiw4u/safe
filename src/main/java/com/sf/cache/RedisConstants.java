package com.sf.cache;

public interface RedisConstants {

	int DEFAULT_EXPIRY_TIME = 604800;
	int REDIS_PORT = 6379;
	int REDIS_SENTINEL_PORT = 26379;
	int REDIS_AUTH_TOKEN_DATABASE = 0;
	int REDIS_MAX_TOTAL = 500;
	int REDIS_MAX_TOTAL_ALL = 250;
	int REDIS_MAX_WAIT_MILIS = 10000;
	int REDIS_MAX_WAIT_MILIS_ALL = 500;
	String KEY_LOCATIONS = "fetch_all_locations";
	String KEY_STATIONS = "fetch_all_stations";
	String KEY_ROUTES = "fetch_all_routes";
	String SESSION_KEY = "session_key";
}
