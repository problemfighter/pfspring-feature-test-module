package com.problemfighter.pfspring.module.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class CacheManagerService {

    @Autowired
    private CacheManager cacheManager;


    public void cleanSingleCache(String name, String key) {
        Objects.requireNonNull(cacheManager.getCache(name)).evict(key);
    }

    public void cleanAllCacheByName(String name) {
        Objects.requireNonNull(cacheManager.getCache(name)).clear();
    }

    public void cleanAllCache() {
        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear();
        }
    }

}
