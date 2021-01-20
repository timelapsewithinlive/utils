package hutool;


import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.date.DateUtil;

/**
 * 说明链接：https://hutool.cn/docs/#/core/%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4/%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4%E5%B7%A5%E5%85%B7-DateUtil
 *
 * 开源：https://www.oschina.net/p/hutool?hmsr=aladdin1e1
 *
 * @author xinghonglin
 * @date 2021/01/20
 */
public class Test {
    public static void main(String[] args) {
        FIFOCache<Object, Object> cache = CacheUtil.newFIFOCache(8, 1000);
        cache.put("x",111);
    }
}
