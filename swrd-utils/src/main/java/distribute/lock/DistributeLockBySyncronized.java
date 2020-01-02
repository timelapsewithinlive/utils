package distribute.lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DistributeLockBySyncronized {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributeLockBySyncronized.class);
    private static final redis.clients.jedis.Jedis jedis = new redis.clients.jedis.Jedis("192.168.155.130", 6379);
    private static final String distributeLock="distribute_lock";
    private static final String seperator="_";
    private static final long expireMsecs = 1000*60;
    private static volatile Map<String,String> KEY_MAP_THREAD_MARK = new ConcurrentHashMap();//防止hashmap在多线程放置元素的过程中产生扩容死链

    private String key;

    public DistributeLockBySyncronized() {
    }

    public DistributeLockBySyncronized(String key) {
        this.key = key;
    }

    //获取锁
    public boolean lock()  {
        try{
            if(StringUtils.isBlank(key)){
                key=distributeLock;
            }
            InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            String hostAddress = address.getHostAddress();//192.168.0.121
            String threadMark=hostAddress+UUID.randomUUID();
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            Long setnx = jedis.setnx(key,  expires+ seperator + hostAddress+seperator+threadMark);
            if(setnx>0){
                KEY_MAP_THREAD_MARK.put(key,hostAddress+seperator+threadMark);
                return  true;
            }

            //超时检测，释放掉其它线程设置的超时的锁
            String currentValueStr = jedis.get(key);
            if (currentValueStr != null && Long.parseLong(currentValueStr.split(seperator)[0]) < System.currentTimeMillis()) {
                String oldValueStr = jedis.getSet(key,expires+ seperator + hostAddress+seperator+ threadMark);//存在两个线程同时运行到这里。分别先后修改key值得情况。会放入其它线程的UUID标识。释放锁，就会无法释放，必须等待锁超时
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    jedis.set(key,expires+ seperator + hostAddress+seperator+ threadMark);//38 到 40行整个算一个cas操作
                    KEY_MAP_THREAD_MARK.put(key,threadMark);
                    return true;
                }
            }
        }catch (Exception e){
            LOGGER.error("获取锁异常：",e);
        }
        return  false;
    }

    //释放锁
    public boolean unlock() {
        if(StringUtils.isBlank(key)){
            key=distributeLock;
        }
        String currentValueStr = jedis.get(key);
        String threadMark = KEY_MAP_THREAD_MARK.get(key);
        if(currentValueStr != null){
            String[] split = currentValueStr.split(seperator);
            if ((split[1]+seperator+split[2]+"").equals(threadMark) ) {
                jedis.del(key);
                return true;
            }
        }
        return  false;
    }

    //业务执行时，保障当前机器的加锁和释放锁在一个事物里执行，事物执行结束前，当前机器的其它线程无法参与
    public synchronized  void bussiness(){
        try{
            boolean lock = lock();
            if(lock){
                //业务
            }
        }catch (Exception e){
            LOGGER.error("业务执行异常：",e);

        }finally {
            boolean unlock = unlock();
            if(!unlock){
                //未释放锁成功，走回滚或者其它补偿逻辑
            }
        }
    }

    public static void main(String[ ] args){
        DistributeLockBySyncronized distributeLockBySyncronized = new DistributeLockBySyncronized();
        distributeLockBySyncronized.bussiness();
    }
}
