package jdk.spi;

import designpatterns.chain.FutureCollector;

import java.io.IOException;
import java.sql.Driver;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class TestSPI {
    FutureCollector o = new FutureCollector(new ConcurrentHashMap<>());

    public static void main(String[] args) throws IOException {
        String bb="b";
        System.in.read();
        ServiceLoader.load(Driver.class);
    }
}
