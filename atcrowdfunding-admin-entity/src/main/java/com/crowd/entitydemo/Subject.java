package com.crowd.entitydemo;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/29 12:03
 */
public class Subject {
    private String name;
    private Integer grade;

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Subject() {
    }

    public Subject(String name, Integer grade) {
        this.name = name;
        this.grade = grade;
    }
}
