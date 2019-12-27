package instrument.agent;

//import com.sun.tools.attach.VirtualMachine;
//import com.sun.tools.attach.VirtualMachineDescriptor;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

public class AttachThread extends Thread {
    private final List<VirtualMachineDescriptor> listBefore;

    private final String jar;

    AttachThread(String attachJar, List<VirtualMachineDescriptor> vms) {
        listBefore = vms;  // 记录程序启动时的 VM 集合
        jar = attachJar;
    }

    public void run() {
        VirtualMachine vm = null;
        List<VirtualMachineDescriptor> listAfter = null;
        try {
            int count = 0;
            while (true) {
                listAfter = VirtualMachine.list();
                for (VirtualMachineDescriptor vmd : listAfter) {
                    if(vmd.displayName().equals("instrument.TestMainInJar")){
                    //if (!listBefore.contains(vmd)) {
                        // 如果 VM 有增加，我们就认为是被监控的 VM 启动了
                        // 这时，我们开始监控这个 VM
                        vm = VirtualMachine.attach(vmd);
                        vm.loadAgent(jar);
                        System.out.println("替换成功");
                        break;
                    }
                }
                Thread.sleep(500);
                count++;
                if (null != vm || count >= 10000000000l) {
                    break;
                }
            }
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //new AttachThread("E:\\work\\code\\secoo-open\\asmtools-master\\out\\artifacts\\instrument\\instrument.jar", VirtualMachine.list()).start();
        new AttachThread("F:\\code\\code-github\\utils\\out\\artifacts\\swrd-example.jar", VirtualMachine.list()).start();

    }
}
