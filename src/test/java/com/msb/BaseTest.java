package com.msb;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import org.junit.Test;

import java.time.Duration;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-05-25 10:36
 */
public class BaseTest {

    @Test
    public void test() {
       // 1. 初始化CacheManager
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("singleDog",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                Object.class,
                                // heap相当于设置数据在堆内存中存储的 个数 或者 大小
                                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10).build()
                        ))
                .build(true);

        // 2.基于CacheManager获取Cache对象
        Cache<String, Object> cache = cacheManager.getCache("singleDog", String.class, Object.class);

        // 3.存
        cache.put("ehcache", "57个单身狗！");

       // 4.取
        System.out.println(cache.get("ehcache"));

    }

    @Test
    public void testDisk() {
        // 声明存储位置
        String path = ".\\ehcache";

        // 1. 初始化CacheManager
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                // 设置disk存储的位置
                .with(CacheManagerBuilder.persistence(path))
                // 设置缓存
                .withCache("singleDog",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                String.class,
                                // heap相当于设置数据在堆内存中存储的 个数 或者 大小
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        // 存储个数
                                        .heap(10)
                                        // 堆外内存
                                        .offheap(10, MemoryUnit.MB)
                                        // 磁盘存储 涉及序列化和反序列化
                                        .disk(11, MemoryUnit.MB, true)
                                        .build()
                        ))
                .build(true);
        // 2.基于CacheManager获取Cache对象
        Cache<String, String> cache = cacheManager.getCache("singleDog", String.class, String.class);

        // 3.存
//        cache.put("ehcache", "57个单身狗！");

        // 4.取
        System.out.println(cache.get("ehcache"));

        // 5.保证数据正常持久化不丢失
        cacheManager.close();

    }

    @Test
    public void testExpire() throws InterruptedException {

        // 1. 初始化CacheManager
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                // 设置缓存
                .withCache("singleDog",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                Object.class,
                                // heap相当于设置数据在堆内存中存储的 个数 或者 大小
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        // 存储个数
                                        .heap(10)
                        )
//                        .withExpiry(ExpiryPolicy.NO_EXPIRY) // 不设置失效时间
//                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMillis(1000))) // 设置生存时间，从存储开始计算
                        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMillis(1000))) // 设置生存时间，每次获取数据后，重置生存时间
                )
                .build(true);
        // 2.基于CacheManager获取Cache对象
        Cache<String, Object> cache = cacheManager.getCache("singleDog", String.class, Object.class);


        cache.put("ehcache", "57个单身狗！");
        // 场景一：设置生存时间，从存储开始计算
        // 立即取
//        System.out.println(cache.get("ehcache"));
//        // 隔断时间再取
//        Thread.sleep(2000);
//        System.out.println(cache.get("ehcache"));

        // 场景二：设置生存时间，每次获取数据后，重置生存时间
        // 取1次
        System.out.println(cache.get("ehcache"));
        Thread.sleep(500);
         // 取2次
        System.out.println(cache.get("ehcache"));
        Thread.sleep(500);
        // 取3次
        System.out.println(cache.get("ehcache"));
        Thread.sleep(500);
        // 取4次
        System.out.println(cache.get("ehcache"));

    }

}
