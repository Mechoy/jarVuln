package com.mechoy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Person implements Serializable {
    private String name;

    private String cmd;

    public String getName() {
        System.out.println("getName");
        return name;
    }

    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
    }

    public String getCmd() {
        System.out.println("getCmd");
        return cmd;
    }

    public void setCmd(String cmd) {
        System.out.println("setCmd");
        this.cmd = cmd;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        System.out.println("readObject..");
        ois.defaultReadObject();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", cmd='" + cmd + '\'' +
                '}';
    }
}
