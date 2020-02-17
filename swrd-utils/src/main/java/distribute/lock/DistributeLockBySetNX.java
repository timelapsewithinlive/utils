/**
@author zuohaoshibuliuming
@date 2017年5月20日---下午2:57:00
@explain:
*/
package distribute.lock;


import org.apache.commons.lang3.StringUtils;

//用redis命令实现分布式锁
public class DistributeLockBySetNX {
	static redis.clients.jedis.Jedis jedis = new redis.clients.jedis.Jedis("192.168.155.130", 6379);
	public static void task() throws Exception{
		String distributeLock="swrd_distribute_lock";
		Long timesOut= System.currentTimeMillis()+900*1000;
		Long now =System.currentTimeMillis();
		try{
			//如果当前时间小于返回上次的设置的时间，那么证明只有该线程获取到锁，否则其它线程事先进行了getSet操作，但是两个线程是同时获取到锁
			//String getSetValue = redisUtils.getSet(distributeLock, String.valueOf(timesOut));
			String result = jedis.setex(distributeLock, 900, String.valueOf(timesOut));//设置时间和设置值为原子操作
			String getValue=jedis.get(distributeLock);
			if(result.equals(1)&& StringUtils.isNotBlank(getValue)&&now>Long.valueOf(getValue)){
				String oldValue=jedis.getSet(distributeLock,String.valueOf(timesOut));
				if(oldValue!=null&&oldValue.equals(getValue)){
					/**
					 * .....................
					 */
				}
			}else{
				return;
			}
		}finally{
			jedis.del(distributeLock);
		}
	}
/*
 * 
 ** 使用SETNX实现分布式锁

多个进程执行以下Redis命令：

SETNX lock.foo <current Unix time + lock timeout + 1>

如果 SETNX 返回1，说明该进程获得锁，SETNX将键 lock.foo 的值设置为锁的超时时间（当前时间 + 锁的有效时间）。 
如果 SETNX 返回0，说明其他进程已经获得了锁，进程不能进入临界区。进程可以在一个循环中不断地尝试 SETNX 操作，以获得锁。

解决死锁
考虑一种情况，如果进程获得锁后，断开了与 Redis 的连接（可能是进程挂掉，或者网络中断），如果没有有效的释放锁的机制，那么其他进程都会处于一直等待的状态，即出现“死锁”。
上面在使用 SETNX 获得锁时，我们将键 lock.foo 的值设置为锁的有效时间，进程获得锁后，其他进程还会不断的检测锁是否已超时，如果超时，那么等待的进程也将有机会获得锁。
然而，锁超时时，我们不能简单地使用 DEL 命令删除键 lock.foo 以释放锁。考虑以下情况，进程P1已经首先获得了锁 lock.foo，然后进程P1挂掉了。进程P2，P3正在不断地检测锁是否已释放或者已超时，执行流程如下：

P2和P3进程读取键 lock.foo 的值，检测锁是否已超时（通过比较当前时间和键 lock.foo 的值来判断是否超时）
P2和P3进程发现锁 lock.foo 已超时
P2执行 DEL lock.foo命令
P2执行 SETNX lock.foo命令，并返回1，即P2获得锁
P3执行 DEL lock.foo命令将P2刚刚设置的键 lock.foo 删除（这步是由于P3刚才已检测到锁已超时）
P3执行 SETNX lock.foo命令，并返回1，即P3获得锁
P2和P3同时获得了锁
从上面的情况可以得知，在检测到锁超时后，进程不能直接简单地执行 DEL 删除键的操作以获得锁。

为了解决上述算法可能出现的多个进程同时获得锁的问题，我们再来看以下的算法。 
我们同样假设进程P1已经首先获得了锁 lock.foo，然后进程P1挂掉了。接下来的情况：

进程P4执行 SETNX lock.foo 以尝试获取锁
由于进程P1已获得了锁，所以P4执行 SETNX lock.foo 返回0，即获取锁失败
P4执行 GET lock.foo 来检测锁是否已超时，如果没超时，则等待一段时间，再次检测
如果P4检测到锁已超时，即当前的时间大于键 lock.foo 的值，P4会执行以下操作 
GETSET lock.foo <current Unix timestamp + lock timeout + 1>
由于 GETSET 操作在设置键的值的同时，还会返回键的旧值，通过比较键 lock.foo 的旧值是否小于当前时间，可以判断进程是否已获得锁
假如另一个进程P5也检测到锁已超时，并在P4之前执行了 GETSET 操作，那么P4的 GETSET 操作返回的是一个大于当前时间的时间戳，这样P4就不会获得锁而继续等待。注意到，即使P4接下来将键 lock.foo 的值设置了比P5设置的更大的值也没影响。
另外，值得注意的是，在进程释放锁，即执行 DEL lock.foo 操作前，需要先判断锁是否已超时。如果锁已超时，那么锁可能已由其他进程获得，这时直接执行 DEL lock.foo 操作会导致把其他进程已获得的锁释放掉。

程序代码

用以下Python代码来实现上述的使用 SETNX 命令作分布式锁的算法。

LOCK_TIMEOUT = 3
lock = 0
lock_timeout = 0
lock_key = 'lock.foo'

# 获取锁
while lock != 1:
    now = int(time.time())
    lock_timeout = now + LOCK_TIMEOUT + 1
    lock = redis_client.setnx(lock_key, lock_timeout)
    if lock == 1 or (now > int(redis_client.get(lock_key))) and now > int(redis_client.getset(lock_key, lock_timeout)):
        break
    else:
        time.sleep(0.001)

# 已获得锁
do_job()

# 释放锁
now = int(time.time())
if now < lock_timeout:
    redis_client.delete(lock_key)
	 */
}
