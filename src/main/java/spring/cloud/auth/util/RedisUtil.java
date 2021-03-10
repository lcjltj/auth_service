package spring.cloud.auth.util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.cloud.auth.exception.DefaultException;
import spring.cloud.auth.exception.DefaultExceptionType;

@Component
@RequiredArgsConstructor
public class RedisUtil {

	@SuppressWarnings("rawtypes")
	private final RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	public <T> void saveRedisForGeneric(String key, T value, int timeout) {
		ValueOperations<String, T> values = redisTemplate.opsForValue();
		values.set(key, value, timeout, TimeUnit.SECONDS);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getRedisForGeneric(T key) {
		ValueOperations<String, T> values = redisTemplate.opsForValue();
		T result = values.get(key);

		if (result == null) {
			throw new DefaultException(DefaultExceptionType.NOT_LOGIN);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveRedisForMap(Object key, Map<Object, Object> map) {
		HashOperations values = redisTemplate.opsForHash();
		for (Object mapKey : map.keySet()) {
			values.put(key, mapKey, map.get(mapKey));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<Object, Object> getRedisForMap(Object key) {
		HashOperations values = redisTemplate.opsForHash();
		Map<Object, Object> result = values.entries(key);

		return result;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteKey(String key) {
		return redisTemplate.delete(key);
	}
}
