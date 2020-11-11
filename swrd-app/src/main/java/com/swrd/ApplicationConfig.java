package com.swrd;

import com.swrd.config.DataSourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author scorpio
 * @version 1.0.0
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({
        DataSourceConfig.class
})
@ComponentScan(basePackages = {
        "designpatterns",
        "com.swrd.controller",
        "spring",
        "jooq"

})
public class ApplicationConfig {

}
