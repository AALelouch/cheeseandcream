package com.lelouch.cheeseandcream.infra.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        CaffeineCache agentsCache = new CaffeineCache("agents",
                        Caffeine.newBuilder()
                        .expireAfterWrite(60, TimeUnit.MINUTES)
                        .maximumSize(100)
                        .build());

        CaffeineCache agentsById = new CaffeineCache("agents-by-id",
                Caffeine.newBuilder()
                        .expireAfterWrite(60, TimeUnit.MINUTES)
                        .maximumSize(100)
                        .build());

        CaffeineCache identificationTypesCache = new CaffeineCache("identificationTypes",
                Caffeine.newBuilder()
                        .expireAfterWrite(60, TimeUnit.MINUTES)
                        .maximumSize(6)
                        .build());

        CaffeineCache categoriesCache = new CaffeineCache("categories",
                Caffeine.newBuilder()
                        .expireAfterWrite(60, TimeUnit.MINUTES)
                        .maximumSize(6)
                        .build());

        cacheManager.setCaches(Arrays.asList(agentsCache, identificationTypesCache, agentsById, categoriesCache));
        return cacheManager;
    }

}
