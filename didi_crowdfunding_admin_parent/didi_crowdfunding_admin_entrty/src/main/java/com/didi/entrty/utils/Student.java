package com.didi.entrty.utils;

import java.util.List;
import java.util.Map;

public class Student {
    private Integer id;
    private  String name;
    private  Integer age;
    private String sex;
    private  Address address;
    private List<Score> list;
    private Map<String,String> map;

    public Student() {

    }
    public Student(Integer id, String name, Integer age, String sex, Address address, List<Score> list, Map<String, String> map) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.list = list;
        this.map = map;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Score> getList() {
        return list;
    }

    public void setList(List<Score> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address=" + address +
                ", list=" + list +
                ", map=" + map +
                '}';
    }
}
