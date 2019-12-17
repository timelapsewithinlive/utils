package jdk.exception;

public class TestException {
    public static void main(String[] args){
        Exception exception = new Exception("虚拟机不处理");
        try {
            throw exception;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
