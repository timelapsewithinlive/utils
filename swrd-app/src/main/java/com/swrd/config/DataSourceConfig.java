package com.swrd.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean("dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.config.test.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.datasource.config.test.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.config.test.password"));
        return dataSource;
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager materialTransactionManager(
            @Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dslContext")
    public DSLContext dslContext(@Qualifier("dataSource") DataSource source) {
        return createDslContext(source);
    }

    public Configuration configuration(DataSource dataSource) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(SQLDialect.MYSQL);
        config.settings().withRenderSchema(false);
        config.set(new TransactionAwareDataSourceProxy(dataSource));
        return config;
    }

    /**
     * 创建JooQ上下文
     *
     * @param dataSource
     * @return
     */
    private DSLContext createDslContext(DataSource dataSource) {
        return DSL.using(configuration(dataSource));
    }



}
