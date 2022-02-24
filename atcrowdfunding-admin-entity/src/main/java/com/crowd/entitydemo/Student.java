package com.crowd.entitydemo;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/29 11:57
 */
public class Student {
    private Integer id;
    private String name;
    private Address address;
    private List<Subject> list;
    private Map<String,String> map;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", list=" + list +
                ", map=" + map +
                '}';
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Subject> getList() {
        return list;
    }

    public void setList(List<Subject> list) {
        this.list = list;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Student(Integer id, String name, Address address, List<Subject> list, Map<String, String> map) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.list = list;
        this.map = map;
    }
}
