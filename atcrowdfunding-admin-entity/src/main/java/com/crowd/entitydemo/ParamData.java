package com.crowd.entitydemo;

import java.util.List;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/29 11:11
 */
public class ParamData {
    private List<Integer> list;

    public ParamData() {
    }

    public ParamData(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ParamData{" +
                "list=" + list +
                '}';
    }
}
