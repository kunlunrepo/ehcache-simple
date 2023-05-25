package com.msb.config;

import com.msb.model.dto.BaseObject;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-05-25 11:38
 */
@Configuration
@EnableCaching
public class EhCacheConfig {

    @Autowired
    private EhCacheProps ehCacheProps;

    @Bean
    public CacheManager ehCacheManager() {
        // 1.缓存名称
        Set<String> cacheNames = ehCacheProps.getCacheNames();

        // 2.设置内存存储位置和数量大小
        ResourcePools resourcePools = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .heap(ehCacheProps.getHeap())
                .offheap(ehCacheProps.getOffheap(), MemoryUnit.MB)
                .disk(ehCacheProps.getDisk(), MemoryUnit.MB)
                .build();

        // 3.设置生存时间
        ExpiryPolicy expiry = ExpiryPolicyBuilder.noExpiration();

        // 4.设置CacheConfiguration
        CacheConfiguration cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, BaseObject.class, resourcePools)
                .withExpiry(expiry)
                .build();

        // 5.设置磁盘存储位置
        CacheManagerBuilder<PersistentCacheManager> cacheManagerBuilder = CacheManagerBuilder
                .newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(ehCacheProps.getDiskDir()));

        // 6.设置缓存名称
        for (String cacheName : cacheNames) {
            cacheManagerBuilder.withCache(cacheName, cacheConfiguration);
        }

        PersistentCacheManager build = cacheManagerBuilder.build();


        // 7.构建
        return cacheManagerBuilder.build();
    }

}
