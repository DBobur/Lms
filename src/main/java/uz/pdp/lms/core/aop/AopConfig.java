package uz.pdp.lms.core.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
    @Bean
    public DefaultServiceLoggingAspect defaultLoggingAspect() {
        return new DefaultServiceLoggingAspect();
    }
    @Bean
    public DefaultControllerLoggingAspect controllerLoggingAspect() {
        return new DefaultControllerLoggingAspect();
    }
}

