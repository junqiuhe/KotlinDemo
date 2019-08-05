package com.java.demo.clazz.annotation;

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/31 9:39
 * Description:
 */
public class Person {

    @IntValue(value = 1000)
    private int id;

    @StringValue(value = "Jackson")
    private String name;

    public Person() {
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
