package io.xream.x7.reyc;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import io.xream.x7.reyc.api.ReyTemplate;
import io.xream.x7.reyc.internal.R4JTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class ReyTemplateConfig {

    @ConditionalOnMissingBean(ReyTemplate.class)
    @Bean
    public ReyTemplate reyTemplate(CircuitBreakerRegistry circuitBreakerRegistry) {
        RetryRegistry retryRegistry = RetryRegistry.ofDefaults();
        return new R4JTemplate(circuitBreakerRegistry,retryRegistry);
    }
}
