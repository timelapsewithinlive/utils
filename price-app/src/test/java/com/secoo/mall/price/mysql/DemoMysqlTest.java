package com.secoo.mall.price.mysql;

import com.secoo.mall.price.ApplicationTest;
import com.secoo.mall.price.dao.mysql.mapper.DemoMapper;
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
public class DemoMysqlTest extends ApplicationTest {
    @Resource
    private DemoMapper demoMapper;
    private long id = 3669L;

    @Test
    public void test() {
        log.info("demoMapper-->{}", demoMapper.selectById(id));
    }

}
