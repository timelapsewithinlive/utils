package com.secoo.mall.price.dubbo;

import com.secoo.mall.price.ApplicationTest;
import com.secoo.mall.price.api.DemoService;
import com.secoo.mall.price.dto.DemoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

/**
 * 模版上线前，请删除
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@Slf4j
public class DemoDubboTest extends ApplicationTest {

    @Reference
    private DemoService demoService;

    @Test
    public void test() {
        DemoDto result = demoService.sayHello("lilei");
        log.info("result:{}", result);
    }
}
