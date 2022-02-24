package com.crowdfunding.crowd.service.impl;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-15 10:33
 **/
public class ProjectServiceImplTest {
    /**
     * 主要是测试日期格式如何转换
     */
    @Test
    public void simpleDateFormatTest1() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = simpleDateFormat.parse("2021-11-10");


        System.out.println(startDate);
        Date nowDate = new Date();
        System.out.println("现在的时间是"+simpleDateFormat.format(nowDate));

        int time = (int) ((nowDate.getTime() - startDate.getTime())/1000/60/60/24);

        int day = 30;

        System.out.println("截至日期："+(day-time));

    }@Test
    public void simpleDateFormatTest2() throws ParseException {
        // 指定SimpleDateFormat日期格式（String<-满足条件->Date）
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 部署时间，在mysql数据库中，也是这样部署的
        String deployDate = "2021-11-1";

        try {
            // 将指定格式的String类型的项目部署时间转成Date类型
            Date startDate = simpleDateFormat.parse(deployDate);
            // 当前时间的Date类型
            Date nowDate = new Date();
            // 两个Date类型获取时间戳，相减，毫秒数，转成天数
            int time = (int) ((nowDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24);
            // 项目众筹天数-已部署天数=截至多少天
            Integer day = 30;
            Integer lastDay = day - time;

            System.out.println(lastDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}