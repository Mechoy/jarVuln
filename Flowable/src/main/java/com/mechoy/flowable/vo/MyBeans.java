package com.mechoy.flowable.vo;

import org.springframework.stereotype.Component;

import java.io.IOException;

public class MyBeans {
    public static void sayHello(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
