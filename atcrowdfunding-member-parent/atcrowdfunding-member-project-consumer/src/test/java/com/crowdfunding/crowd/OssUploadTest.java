package com.crowdfunding.crowd;

import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.properties.OSSProperties;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-03 22:38
 **/

public class OssUploadTest {


    @Test
    public void uploadTest() throws FileNotFoundException {
//        FileInputStream inputStream = new FileInputStream("/lightning-1625550_1280.jpg");
//        ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
//                "httP://oss-cn-hangzhou.aliyuncs.com",
//                "my-bucket21112",
//              "my-bucket21112.oss-cn-hangzhou.aliyuncs.com",
//                "LTAI5tE5sj3oSKYjKd1ywYpP",
//                "9z2vzzVnlM6kHaprDW6asr0yhaARGf",
//                inputStream,
//                "hhhhhhhh.jpg"
//        );
//        System.out.println(resultEntity.getData());
    }
}
