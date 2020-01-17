package jdk.bit;

public class TestBit {

    public static void main(String[] args){
        System.out.println(2>>1);
        System.out.println((2>>1) & 1);
        System.out.println((1<<11));
        System.out.println(5^3);
        System.out.println(test());
    }

    public static String test(){
        try {
            return "先返回";
        }finally {
            System.out.println("先final");
        }
    }
}
