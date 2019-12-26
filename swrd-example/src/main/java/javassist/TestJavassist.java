package javassist;

import com.alibaba.ttl.internal.javassist.CannotCompileException;
import com.alibaba.ttl.internal.javassist.ClassPool;
import com.alibaba.ttl.internal.javassist.CtClass;
import com.alibaba.ttl.internal.javassist.NotFoundException;

import java.io.IOException;

public class TestJavassist {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("zookeeper.TestCurator");
        cc.setSuperclass(pool.get("zookeeper.SuperTest"));
        cc.writeFile();
    }
}
