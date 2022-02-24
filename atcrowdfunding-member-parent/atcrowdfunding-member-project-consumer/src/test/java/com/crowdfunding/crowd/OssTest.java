package com.crowdfunding.crowd;

import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.properties.OSSProperties;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-03 17:42
 **/
public class OssTest {

    // 测试上传文件目录
    @Test
    public void uploadFileDirectoryNameTest(){
        // 1.simpleDateFormat生成的日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // 2.将当前Date转换成simpleDateFormat所规定的格式
        String format = dateFormat.format(new Date());
        // 当前Date类型
        Date date = new Date();
        // 通过Date类型获取时间戳
        long time = date.getTime();

        System.out.println("dateFormat:"+dateFormat);
        System.out.println("dateFormat.format:"+format);
        System.out.println("date:"+date);
        System.out.println(time);
    }

    // 测试上传文件名称
    @Test
    public void uploadFileNameTest(){
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid:"+uuid);
        System.out.println("uuid的类型:"+uuid.getClass());
        System.out.println("uuid去除'-':"+uuid.toString().replaceAll("-",""));

    }

    @Test
    public void uploadFileNameSuffixTest(){

        String image = "beautifulGirl.jpg";
        System.out.println("图片的后缀1:"+image.substring(image.indexOf(".")));
        System.out.println("图片的后缀2:"+image.substring(image.lastIndexOf(".")));

        String[] split = image.split("'.'");

        String[] fs = image.split("f");
        System.out.println("按f分割后字符串:"+fs[1]);

        if(split == null || split.length == 0){

        }else{
            System.out.println(split);
            System.out.println(split.length);
            System.out.println(split[0]);
            System.out.println("分割失败");
//            String suffix = split[1];
//            System.out.println("图片的后缀3:"+suffix);
        }

    }


}
