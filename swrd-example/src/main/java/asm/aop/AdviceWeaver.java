package asm.aop;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.JSRInlinerAdapter;

public class AdviceWeaver extends ClassVisitor {

    public AdviceWeaver(int i, ClassVisitor classVisitor) {
        super(i,classVisitor);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        signature="5342";
        MethodVisitor mv = cv.visitMethod(access,  name,  desc,  signature,  exceptions);
        JSRInlinerAdapter jsrInlinerAdapter = new JSRInlinerAdapter(mv, access, name, desc, signature, exceptions);
        mv = new TraceAdviceAdapter(Opcodes.ASM5, jsrInlinerAdapter, access, name, desc);
        return mv;
    }


}
