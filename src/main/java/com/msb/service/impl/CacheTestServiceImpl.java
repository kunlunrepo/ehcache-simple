package com.msb.service.impl;

import com.msb.service.CacheTestService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-05-25 14:10
 */
@Service
public class CacheTestServiceImpl implements CacheTestService {


    @Override
    // 第一种：单纯以key判断
//    @Cacheable(cacheNames = {"item"}, key="#id")
    // 第二种：以keyGenerator判断
//    @Cacheable(cacheNames = {"item"}, keyGenerator = "itemKeyGenerator")
    // 第三种：在执行方法前，决定是否需要缓存
//    @Cacheable(cacheNames = {"item"}, condition = "#id.equals(\"123\")")
    // 第四种：在执行方法后，决定是否需要缓存
//    @Cacheable(cacheNames = {"item"}, unless = "#result.equals(\"123\")") // 使用场景较少
    @Cacheable(cacheNames = {"item"}, sync = true) // 如果没有缓存数据，就去执行业务代码，后续线程等待前置线程执行完，再去直接查询缓存
    public String echo(String id, String... args) {

        System.out.println("查询数据库");

        return id;
    }

    @Override
    @CacheEvict(value = "item")
    public void clear(String id) {
        System.out.println("清楚缓存成功！");
    }

    @Override
//    @CacheEvict(value = "item", allEntries = true)
    @CacheEvict(value = "item", allEntries = true, beforeInvocation = true) // beforeInvocation 在方法执行之前就将缓存正常清除
    public void clearAll() {
        System.out.println("清除item中的全部缓存");
    }




}
