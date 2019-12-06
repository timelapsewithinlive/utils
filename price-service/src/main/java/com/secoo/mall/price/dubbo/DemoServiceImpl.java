package com.secoo.mall.price.dubbo;

import com.secoo.mall.price.api.DemoService;
import com.secoo.mall.price.dto.DemoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * 模版上线前，请删除
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

   @Override
    public DemoDto sayHello(String name) {
        DemoDto demo = new DemoDto();
        demo.setHello("zhangsan");
        log.info("sayHello:{}" + name);
        return demo;
    }

}
