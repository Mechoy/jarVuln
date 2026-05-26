package com.mechoy;

public class TestUser {
    public String name;
    public int age;
    
    public Object child;

  public TestUser() {
  }

  public TestUser(String n, int a, Object o){
    this.name=n;
    this.age=a;
    this.child=o;
  }

  public void setName(String n){
    this.name=n;
  }

  public String getName(){
    return this.name;
  }

  public void setAge(int a){
    this.age=a;
  }
  public int getAge(){
    return this.age;
  }

  public Object getChild() {
    return child;
  }

  public void getChild(Object child) {
    this.child = child;
  }

}
