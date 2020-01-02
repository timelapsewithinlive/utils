/**
@author zuohaoshibuliuming
@date 2017年7月10日---下午5:51:27
@explain:
*/
package distribute.lock;

import java.lang.Thread.State;

public class Test {
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(true){
						
						try {
							Thread.currentThread().sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						throw new RuntimeException("异常了");
					}
				}catch(Exception E){
					System.out.println(E.getMessage());
				}finally{
					System.out.println("必须执行");
					State state = Thread.currentThread().getState();
					System.out.println(state.valueOf(state.NEW.name()));
				}
			}
		}).start();
		
	}
}
