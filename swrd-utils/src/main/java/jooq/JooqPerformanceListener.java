package jooq;

import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.Query;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.tools.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 基于jooq的数据库执行性能监听器
 *
 */
public class JooqPerformanceListener extends DefaultExecuteListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.sm.audit.commons.jooq.JooqPerformanceListener.class);

    private static String SHOW_SQL = System.getProperty("jooq.show-sql", "false");

    private static String SLOW_QUERY_TIME = System.getProperty("jooq.slow-query-time", "5000");

    private StopWatch stopWatch;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        SHOW_SQL = environment.getProperty("jooq.show-sql", "false");
        SLOW_QUERY_TIME = environment.getProperty("jooq.slow-query-time", "5000");
    }

    @Override
    public void executeStart(ExecuteContext ctx) {
        super.executeStart(ctx);
        stopWatch = new StopWatch();
    }

    @Override
    public void executeEnd(ExecuteContext ctx) {
        super.executeEnd(ctx);
        // Create a new DSLContext for logging rendering purposes
        DSLContext dslContext = DSL.using(ctx.dialect(),
            SettingsTools.clone(ctx.settings()).withRenderFormatted(false));
        if (Boolean.valueOf(SHOW_SQL)) {
            LOGGER.info("jOOQ Meta executed:\n{}", formatted(dslContext, ctx.query()));
        } else if (stopWatch.split() > TimeUnit.MILLISECONDS.toNanos(Long.parseLong(SLOW_QUERY_TIME))) {
            LOGGER.warn("jOOQ Meta executed a slow query:\n{}", formatted(dslContext, ctx.query()));
        }
    }

    @Override
    public void renderEnd(ExecuteContext ctx) {
        if (ctx.sql().matches("^(?i:(UPDATE|DELETE)(?!.* WHERE ).*)$")) {
            throw new DeleteOrUpdateWithoutWhereException(ctx.sql());
        }
    }

    private String formatted(DSLContext dslContext, Query query) {
        return dslContext.renderInlined(query);
    }

    public static class DeleteOrUpdateWithoutWhereException extends RuntimeException {

        public DeleteOrUpdateWithoutWhereException() {
            super();
        }

        public DeleteOrUpdateWithoutWhereException(String message) {
            super(message);
        }
    }
}
