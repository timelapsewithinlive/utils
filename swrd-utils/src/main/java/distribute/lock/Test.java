/**
@author zuohaoshibuliuming
@date 2017年7月10日---下午5:51:27
@explain:
*/
package distribute.lock;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
	public static void main(String[] args) {

		//下面是 JAVA8的处理方式

		/*List<String> list2=new ArrayList<>(Arrays.asList("ab","cd","11","ef"));
			List<String> list3=list2
					.stream() //得到流,准备进行流处理
					.filter(x->!x.equals("11")) // 过滤掉等于"11"的元素
					.collect(Collectors.toList()); // 收集起来
			System.out.println(list3);*/

		//list2.stream().allMatch()
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					if (true) {

						try {
							Thread.currentThread().sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						throw new RuntimeException("异常了");
					}
				} catch (Exception E) {
					System.out.println(E.getMessage());
				} finally {
					System.out.println("必须执行");
					State state = Thread.currentThread().getState();
					System.out.println(state.valueOf(state.NEW.name()));
				}
			}
		});
		thread.start();

		//thread.start();

	}
}
