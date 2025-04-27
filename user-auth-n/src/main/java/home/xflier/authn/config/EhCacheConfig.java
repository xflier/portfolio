package home.xflier.authn.config;

import org.springframework.context.annotation.Configuration;

/**
 * EhCache configuration class. 
 * Another way to configure CacheManager Bean
 */
@Configuration
public class EhCacheConfig {

//     @Bean
//     CacheManager cacheManager() throws Exception {
//         CachingProvider cachingProvider = Caching.getCachingProvider();

//         URI uri = getClass().getResource("/ehcache.xml").toURI();
//         CacheManager cacheManager = cachingProvider.getCacheManager(uri, getClass().getClassLoader());

//         return cacheManager;
//     }

    // @Bean (name = "cacheManager2")
    // @Primary
    // public javax.cache.CacheManager cacheManager() throws Exception {
    // CachingProvider cachingProvider = Caching.getCachingProvider();
    // javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager();

    // javax.cache.configuration.Configuration<Integer, RoleOutDto> config =
    // Eh107Configuration.fromEhcacheCacheConfiguration(
    // CacheConfigurationBuilder.newCacheConfigurationBuilder(
    // Integer.class, RoleOutDto.class,
    // ResourcePoolsBuilder.heap(1000)
    // ).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)))
    // .build()
    // );

    // cacheManager.createCache("roleCacheById", config);

    // return cacheManager;
    // }

}
