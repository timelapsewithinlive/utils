package jooq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author xinghonglin
 * @date 2020/11/11
 */
@Slf4j
@Transactional
@Service
@Component
public class JooqTest {
    @Resource(name = "dslContext")
    private DSLContext dslContext;

    @PostConstruct
    public void init() {
        try {
            log.info("dslContext: " + dslContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
