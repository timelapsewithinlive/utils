package javassist;

import com.alibaba.ttl.internal.javassist.*;
import com.alibaba.ttl.internal.javassist.bytecode.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class TestJavassist {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, BadBytecode {
        //ClassPool pool = ClassPool.getDefault();
        //CtClass cc = pool.get("zookeeper.TestCurator");
        //cc.setSuperclass(pool.get("zookeeper.SuperTest"));
        //cc.writeFile();
        /*CtMethod[] declaredMethods = cc.getDeclaredMethods();
        for(CtMethod ctMethod:declaredMethods) {
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            System.out.println(JSON.toJSONString(methodInfo));
        }*/
        BufferedInputStream fin
                = new BufferedInputStream(new FileInputStream("E:\\TestCurator.class"));
        ClassFile cf = new ClassFile(new DataInputStream(fin));
        MethodInfo minfo = cf.getMethod("main");    // we assume move is not overloaded.
        CodeAttribute ca = minfo.getCodeAttribute();
        CodeIterator ci = ca.iterator();
        while (ci.hasNext()){
            int index = ci.next();
            int op = ci.byteAt(index);
            System.out.println(Mnemonic.OPCODE[op]);
        }
    }
}
