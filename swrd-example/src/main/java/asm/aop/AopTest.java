package asm.aop;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;


public class AopTest {

    public static void main(String[] args ) throws IOException {
        System.out.println("begin");
        advice();

    }

    public static void advice()throws IOException{
        ClassReader cr = new ClassReader(Calculate.class.getName());
        ClassWriter cw = new ClassWriter(cr, COMPUTE_FRAMES | COMPUTE_MAXS);
        AdviceWeaver myClassVisitor = new AdviceWeaver(Opcodes.ASM5,cw);
        cr.accept(myClassVisitor,0);
        byte[] bytes = cw.toByteArray();
        File file = new File("E:\\asm.class");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();

        System.out.println(new String(bytes));
    }
}
