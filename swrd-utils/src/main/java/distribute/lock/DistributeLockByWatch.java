/**
@author zuohaoshibuliuming
@date 2017年7月10日---下午6:12:02
@explain:
*//*

package distribute.lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Transaction;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DistributeLockByWatch {
	private static final Logger LOGGER = LoggerFactory.getLogger(DistributeLockByGetSet.class);
	private static final redis.clients.jedis.Jedis jedis = new redis.clients.jedis.Jedis("192.168.155.130", 6379);
	private static final String distributeLock="swrd_distribute_lock";
	private static final String seperator="_";
	private static final long expireMsecs = 1000*60;
	private static volatile Map<String,String> KEY_MAP_THREAD_MARK = new ConcurrentHashMap();//防止hashmap在多线程放置元素的过程中产生扩容死链

	private String key;

	public DistributeLockByWatch() {
	}

	public DistributeLockByWatch(String key) {
		this.key = key;
	}

	//获取锁
	public boolean lock()  {
		try{
			if(StringUtils.isBlank(key)){//如果key为空使用默认得key
				key=distributeLock;
			}
			String hostAddress = InetAddress.getLocalHost().getHostAddress();//获取的是本地的IP地址,作分布式实例之间得区分
			String threadMark=UUID.randomUUID()+"";//实例内，每次请求得唯一标识，也是避免本机并发得标识
            long expires = System.currentTimeMillis() + expireMsecs;//过期时间

            String value = expires+seperator+hostAddress+seperator+threadMark;//前三者拼接串

            Long setnx = jedis.setnx(key,value);//setNx得特点，不存在就设置成功，存在就设置失败
			if(setnx>0){
				KEY_MAP_THREAD_MARK.put(key,value);//将本机本次请求获取得锁标识放入本地内存，释放锁时需要进行值比较，才能安全释放
				return  true;
			}

			//超时检测，释放掉其它线程设置的超时的锁
			String currentValueStr = jedis.get(key);
			if (currentValueStr != null && Long.parseLong(currentValueStr.split(seperator)[0]) < System.currentTimeMillis()) {
				String watch = jedis.watch(key);
				Transaction multi = jedis.multi();
				multi.set(key,value);
				multi.exec();
				KEY_MAP_THREAD_MARK.put(key,value);
				return true;
			}
		}catch (Exception e){
			LOGGER.error("获取锁异常：",e);
		}finally {
			jedis.unwatch();
		}
		return  false;
	}

	//释放锁
	public boolean unlock() {
		try{
            if(StringUtils.isBlank(key)){
                key=distributeLock;
            }

			//假如watch得是A得旧值，那么会进入判断。如果超时被B修改。监控得是新值。则不会进入if判断。所以应该不会存在安全问题
			String watch = jedis.watch(key);//事务解决防止分布式中A线程准备del锁的时候，其它线程getSet锁。会导致线程互删锁操作
			String redisValueStr = jedis.get(key);
            String localValueStr = KEY_MAP_THREAD_MARK.get(key);
            if(redisValueStr != null&&redisValueStr.equals(localValueStr)){

                Transaction multi = jedis.multi();
                //multi.del(key);//当master宕机后，A线程的watch机制失效，那么B线程的的setNx就会成功，就可能发生A删B锁的情况，怎么办?请指教
                //del用eval放入redis执行,并且在eval中必须要判断当前要删除的key值是不是跟自己设置的相等。eval每次根据key可以固定在一台机器上执行
                String script="local key = KEYS[1]\r\n"+
                        "local localValueStr = ARGV[1]\r\n"+
                        "local redisValueStr = redis.call('get', key)\r\n"+
                        "if redisValueStr == localValueStr then\r\n"+ //lua 字符串的比较 是检测字符串的hash是否一样来判断两个字符串是否相等
                        "redis.call('del',key)\r\n"+
                        "end";
                List<String> keys = new ArrayList<String>();
                keys.add(key);
                List<String> args = new ArrayList<String>();
                args.add(localValueStr);
                multi.eval(script,keys,args);
                multi.exec();//当A线程走到释放锁事物。B线程走到超时获取锁时。只能有一个成功。使用了事物互斥特性
                return true;
            }
        }catch (Exception e){
            LOGGER.error("释放锁异常：",e);
            return false;
        }finally {
            jedis.unwatch();
        }
		return  false;
	}

	//业务执行时，保障当前机器的加锁和释放锁在一个事物里执行，事物执行结束前，当前机器的其它线程无法参与
	public synchronized void bussiness(){//防止本机内A线程准备del锁的时候，其它线程getSet锁。会导致本机内线程互删锁操作。但是分布式中还存在此问题
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
		DistributeLockByWatch distributeLockByWatch = new DistributeLockByWatch();
		distributeLockByWatch.bussiness();
	}
}
*/
