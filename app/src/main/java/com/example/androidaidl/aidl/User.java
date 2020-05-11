package com.example.androidaidl.aidl;

/**
 * author:lgh on 2020-05-11 13:25
 */
public class User {

    private String name;

    private int sex;

    public User() {
    }

    public User(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
