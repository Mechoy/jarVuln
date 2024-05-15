package com.mechoy.bean;

import io.swagger.models.auth.In;

public class Person {
    private String name;

    private Integer age;

    private Integer cmd;

    public Person() {
        System.out.println("Calling an unarmed constructor");
    }

    public Person(String name, Integer age, Integer cmd) {
        System.out.println("Calling the full parameter constructor");
        this.name = name;
        this.age = age;
        this.cmd = cmd;
    }

    public String getName() {
        System.out.println("Calling getName");
        return name;
    }

    public void setName(String name) {
        System.out.println("Calling setName");
        this.name = name;
    }

    public Integer getAge() {
        System.out.println("Calling getAge");
        return age;
    }

    public void setAge(Integer age) {
        System.out.println("Calling setAge");
        this.age = age;
    }

    public Integer getCmd() {
        System.out.println("Calling getCmd");
        return cmd;
    }

    public void setCmd(Integer cmd) {
        System.out.println("Calling setCmd");
        this.cmd = cmd;
    }
}
