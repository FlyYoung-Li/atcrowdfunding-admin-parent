package com.crowdfunding.crowd.entity.po;


import com.crowdfunding.crowd.entity.testentity.MemberTestPO;
import org.junit.Test;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-20 13:40
 **/
public class MemberTestPOTest {

    @Test
    public void testToString() {
        System.out.println(new MemberTestPO("key",55));
    }
}