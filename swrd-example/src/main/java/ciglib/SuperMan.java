package ciglib;

public class SuperMan implements Human {

    @Override
    public void info() {
        System.out.println("我是超人！我怕谁！");
    }

    @Override
    public void fly() {
        System.out.println("I believe I can fly!");
    }

    @Override
    public void self() {
        System.out.println("this is suman's method--self !");
    }
}
