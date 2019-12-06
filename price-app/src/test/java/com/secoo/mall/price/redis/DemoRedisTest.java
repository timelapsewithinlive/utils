package com.secoo.mall.price.redis;

import com.secoo.mall.price.ApplicationTest;
import com.secoo.mall.price.dao.redis.DemoRedis;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 模版上线前，请删除
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@Slf4j
public class DemoRedisTest extends ApplicationTest {
    @Resource
    private DemoRedis demoRedis;
    private String key = "demo_key";

    @Test
    public void test() {
        log.info("demoRedis-->{}", demoRedis.incAndGet(key));
    }

}
