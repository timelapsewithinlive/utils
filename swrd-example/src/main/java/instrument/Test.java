package instrument;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import jdk.runtime.TestRuntime;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws AgentLoadException, Exception {
        try {
            // attach to target VM
            VirtualMachine vm = VirtualMachine.attach("4516");

            // get system properties in target VM
            Properties propss = vm.getSystemProperties();

            // construct path to management agent
            String home = "java.home";
            String agent = home + File.separator + "lib" + File.separator
                    + "management-agent.jar";

            // load agent into target VM
            vm.loadAgent(agent, "com.sun.management.jmxremote.port=5000");

            // detach
            vm.detach();
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class cls= TestRuntime.class;
        // 获取监控主机
        MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
        // 取得所有在活动的虚拟机集合
        Set<?> vmlist = new HashSet<Object>(local.activeVms());
        // 遍历集合，输出PID和进程名
        for(Object process : vmlist) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
            // 获取类名
            String processname = MonitoredVmUtil.mainClass(vm, true);
            if(cls.getName().equals(processname)) {
                int intValue = ((Integer)process).intValue();
                System.out.println(intValue);
            }
        }
    }
}
