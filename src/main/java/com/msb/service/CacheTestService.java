package com.msb.service;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-05-25 14:09
 */
public interface CacheTestService {

    String echo(String id, String... args);

    void clear(String id);

    void clearAll();

}
