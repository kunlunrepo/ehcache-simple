package com.msb.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-05-25 14:29
 */
@Configuration
public class CacheKeyGenerator {

    @Bean(name="itemKeyGenerator")
    public KeyGenerator itemKeyGenerator() {

        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName()+params[0];
            }
        };
    }

}
