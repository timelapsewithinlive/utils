/**
@author zuohaoshibuliuming
@date 2017年7月10日---下午6:12:02
@explain:
*/
package distribute.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

public class DistributeLockByWatch {
	static  Jedis jedis = new Jedis("192.168.155.130", 6379);
	public static void task() throws Exception{
		String distributeLock="distribute_lock";
		Long timesOut= System.currentTimeMillis()+900*1000;
		Long now =System.currentTimeMillis();
		try{
			Jedis jedis = new Jedis("192.168.155.130", 6379);
			String set = jedis.set(distributeLock,String.valueOf(timesOut));
			String watch = jedis.watch(distributeLock);
			Transaction multi = jedis.multi();
		}finally{
		}
	}

	public static void main(String[] args) throws Exception{

		//System.out.println(jedis.getbit("haha",jedis.hashCode()));
		jedis.setbit("xixi",jedis.hashCode(),"true");
		System.out.println(jedis.getbit("xixi",jedis.hashCode()));
		Pipeline pipelined = jedis.pipelined();
        String s = jedis.clusterAddSlots();
    }
}
