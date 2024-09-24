package com.mechoy.flowable.vo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JavaBeans {
    public void sayHello(String s){
        System.out.println("Hello" + s);
    }
}
