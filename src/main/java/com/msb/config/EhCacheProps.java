package com.msb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-05-25 11:34
 */
@Component
@ConfigurationProperties(prefix = "ehcache")
public class EhCacheProps {

    private int heap;

    private int offheap;

    private int disk;

    private String diskDir;

    private Set<String> cacheNames;


    public int getHeap() {
        return heap;
    }

    public void setHeap(int heap) {
        this.heap = heap;
    }

    public int getOffheap() {
        return offheap;
    }

    public void setOffheap(int offheap) {
        this.offheap = offheap;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public String getDiskDir() {
        return diskDir;
    }

    public void setDiskDir(String diskDir) {
        this.diskDir = diskDir;
    }

    public Set<String> getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(Set<String> cacheNames) {
        this.cacheNames = cacheNames;
    }
}
