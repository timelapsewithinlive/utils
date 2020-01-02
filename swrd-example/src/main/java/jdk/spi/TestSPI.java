package jdk.spi;

import designpatterns.chain.FutureCollector;
import json.Test;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class TestSPI {
    FutureCollector o = new FutureCollector(new ConcurrentHashMap<>());

    public static void main(String[] args) throws IOException {
        String bb="b";
        System.in.read();
    }
}
