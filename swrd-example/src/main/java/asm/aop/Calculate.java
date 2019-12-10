package asm.aop;

import java.util.ArrayList;

public class Calculate {
    public static void main(String[] args ){
        ArrayList<Object> objects = new ArrayList<Object>();
        objects.add(1);
        Done.get();
    }
}
