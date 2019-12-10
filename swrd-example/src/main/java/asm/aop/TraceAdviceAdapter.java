package asm.aop;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

public class TraceAdviceAdapter extends AdviceAdapter {
    final Type ASM_TYPE_SPY = Type.getType("Ljava/lang/System;");
    final Type ASM_TYPE_METHOD = Type.getType(java.lang.reflect.Method.class);
    private final Type ASM_TYPE_OBJECT = Type.getType(Object.class);
    private final Method ASM_METHOD_METHOD_INVOKE = Method.getMethod("Object invoke(Object,Object[])");


    protected TraceAdviceAdapter(int i, MethodVisitor methodVisitor, int i1, String s, String s1) {
        super(i, methodVisitor, i1, s, s1);
    }

    @Override
    protected void onMethodEnter() {
        if(true){
            return;
        }
        loadAdviceMethod();
        pushNull();
        //invokeVirtual(ASM_TYPE_METHOD, ASM_METHOD_METHOD_INVOKE);
    }

    @Override
    protected void onMethodExit(int opcode) {
        //loadAdviceMethod();
    }

    @Override
    public void visitMethodInsn(int opcode, final String owner, final String name, final String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    private void _debug(final StringBuilder append, final String msg) {
        visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    private void loadAdviceMethod() {
        getStatic(ASM_TYPE_SPY, "currentTimeMillis", ASM_TYPE_METHOD);
    }





    private void pushNull() {
        push((Type) null);
    }

    /*
     * 加载return通知参数数组
     */
    private void loadReturnArgs() {
        dup2X1();
        pop2();
        push(1);
        newArray(ASM_TYPE_OBJECT);
        dup();
        dup2X1();
        pop2();
        push(0);
        swap();
        arrayStore(ASM_TYPE_OBJECT);
    }
}
