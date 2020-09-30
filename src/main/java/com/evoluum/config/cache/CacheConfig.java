package com.evoluum.config.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {

	@Autowired
	private CacheManager cacheManager;

	@Scheduled(fixedRate = 100000)
	public void cleanCache() {

		for (String cache : cacheManager.getCacheNames()) {
			cacheManager.getCache(cache).clear();
		}
	}

}
