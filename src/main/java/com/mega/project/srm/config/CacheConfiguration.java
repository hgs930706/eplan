package com.mega.project.srm.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mega.project.srm.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mega.project.srm.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mega.project.srm.domain.User.class.getName());
            createCache(cm, com.mega.project.srm.domain.Authority.class.getName());
            createCache(cm, com.mega.project.srm.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mega.project.srm.domain.ProductionPlanHeader.class.getName());
            createCache(cm, com.mega.project.srm.domain.ProductionPlanHeader.class.getName() + ".details");
            createCache(cm, com.mega.project.srm.domain.ProductionPlanDetails.class.getName());
            createCache(cm, com.mega.project.srm.domain.MaterialUsage.class.getName());
            createCache(cm, com.mega.project.srm.domain.MaterialUsageSummaryHeader.class.getName());
            createCache(cm, com.mega.project.srm.domain.PurchasePlanHeader.class.getName());
            createCache(cm, com.mega.project.srm.domain.PurchasePlanHeader.class.getName() + ".details");
            createCache(cm, com.mega.project.srm.domain.PurchasePlanDetails.class.getName());
            createCache(cm, com.mega.project.srm.domain.Inventory.class.getName());
            createCache(cm, com.mega.project.srm.domain.PoHeader.class.getName());
            createCache(cm, com.mega.project.srm.domain.PoHeader.class.getName() + ".details");
            createCache(cm, com.mega.project.srm.domain.PoDetails.class.getName());
            createCache(cm, com.mega.project.srm.domain.InstalmentPlanHeader.class.getName());
            createCache(cm, com.mega.project.srm.domain.InstalmentPlanHeader.class.getName() + ".details");
            createCache(cm, com.mega.project.srm.domain.InstalmentPlanDetails.class.getName());
            createCache(cm, com.mega.project.srm.domain.InstalmentPlanDetails.class.getName() + ".actuals");
            createCache(cm, com.mega.project.srm.domain.InstalmentPlanDetailActual.class.getName());
            createCache(cm, com.mega.project.srm.domain.OutStockSetup.class.getName());
            createCache(cm, com.mega.project.srm.domain.DeliveryCycleSetup.class.getName());
            createCache(cm, com.mega.project.srm.domain.SummaryMaterialReport.class.getName());
            createCache(cm, com.mega.project.srm.domain.ProductionPlanHeaderHis.class.getName());
            createCache(cm, com.mega.project.srm.domain.ProductionPlanHeaderHis.class.getName() + ".details");
            createCache(cm, com.mega.project.srm.domain.ProductionPlanDetailHis.class.getName());
            createCache(cm, com.mega.project.srm.domain.PurchasePlanHeaderHis.class.getName());
            createCache(cm, com.mega.project.srm.domain.PurchasePlanHeaderHis.class.getName() + ".details");
            createCache(cm, com.mega.project.srm.domain.PurchasePlanDetailHis.class.getName());
            createCache(cm, com.mega.project.srm.domain.InventoryHis.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
