package com.jiagouedu.core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * ehcache cache provider
 * @author wukong 图灵学院 QQ:245553999
 */
public class EhcacheCacheProvider implements CacheProvider {

    private CacheManager cacheManager;
    private String cacheName;
    private Cache cache;
    /**
     * ehcache配置文件
     */
    private Resource configLocation;
    @Override
    public void put(String key, Serializable cacheObject) {
        cache.put(new Element(key, cacheObject));
    }

    @Override
    public Serializable get(String key) {
        Element element = cache.get(key);
        return element != null ? element.getValue() : null;
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.removeAll();
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Resource getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    @PostConstruct
    public void postCacheManager() throws IOException, CacheException {

        InputStream is = (this.configLocation != null ? this.configLocation.getInputStream() : null);
        try {
            // A bit convoluted for EhCache 1.x/2.0 compatibility.
            // To be much simpler once we require EhCache 2.1+
            Configuration configuration = (is != null ? ConfigurationFactory.parseConfiguration(is) :
                    ConfigurationFactory.parseConfiguration());
            this.cacheManager = cacheManager != null ? cacheManager : new CacheManager(configuration);
            // For strict backwards compatibility: use simplest possible constructors...
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
        if(cacheManager == null ) {
            throw new CacheException("cache manager初始化失败");
        }
        cache = cacheManager.getCache(cacheName);
        if(cache == null ) {
            throw new CacheException("cache manager初始化失败");
        }
    }
}
