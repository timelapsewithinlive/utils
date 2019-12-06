package com.secoo.mall.price.dao.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 模版上线前，请删除
 *
 * @author zhanghao
 * @date 2019-10-2613:02
 */
@Repository
public class DemoRedis {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public long incAndGet(String key){
        return redisTemplate.opsForValue().increment(key);
    }
}
