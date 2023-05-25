package com.msb.controller;

import com.msb.service.CacheTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-05-25 14:13
 */
@RestController
public class CacheTestController {

    @Autowired
    CacheTestService cacheTestService;


    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public String echo(@RequestParam("id") String id, @RequestParam("args")String... args) {
        return cacheTestService.echo(id, args);
    }


}
