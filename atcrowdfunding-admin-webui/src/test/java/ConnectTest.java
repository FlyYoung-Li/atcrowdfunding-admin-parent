import com.crowd.entity.Admin;
import com.crowd.entity.Role;
import com.crowd.mapper.AdminMapper;
import com.crowd.mapper.RoleMapper;
import com.crowd.service.api.AdminService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 8:06
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class ConnectTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void testAddRoles(){
        for (int i = 246; i < 500; i++) {
            roleMapper.insert(new Role(null,"role"+i));
        }
    }
    @Test
    public void test01() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void test02() {
        int insert = adminMapper.insert(new Admin(null, "lx9", "lxy123456", "杨魔王", "123915490@qq.com", new Date().toString().substring(0,5)));
        System.out.println(insert);
    }

    @Test
    public void test03(){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("haha");
        Gson gson = new Gson();
        Admin admin = new Admin(3, "哈哈", "密码", "username", "email@qq.com", null);
        String json = gson.toJson(admin);
        logger.info("看看这是json字符串"+json);

    }
    @Test
    public void test05(){
        adminService.saveAdmin(new Admin(null,"lx8","lxf132","憨货","934111@qq.com",new Date().toString().substring(0,5)));
        System.out.println("hh");

    }
    /*下面的测试，与项目无关，只是面试题目，自己实际操作看一下*/
    /*这里进行测试，因为没有加break，所以匹配完之后，直接进行后面的操作了。*/
    @Test
    public void test06(){
        System.out.println(getValue(2));
    }
    public int getValue(int i) {
        int result = 0;
        switch (i) {
            case 1:
                result = result + i;
            case 2:
                result = result + i  * 2;
            case 3:
                result = result + i * 3;
        }
        return result;
    }

    @Test
    public void test07(){
        /*System.out.println(convert());*/
        //这里出现了空指针异常，因为是引用数据类型，Integer赋值为null,会出现空指针异常
    }
    public int convert(){
        int i;
        Integer j = null;
        i = j;
        return i;
    }

    /**
     * 测试AdminMapper中分页查询
     */
    @Test
    public void test8(){
        List<Admin> admins = adminMapper.selectAdminByKeyword("魔王");
        System.out.println("查到的个数："+admins.size());
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    /**
     *  测试，往数据库中加一下数据(性能极低)
     */
    @Test
    public void test9(){
        for (int i = 600; i < 650; i++) {
            adminMapper.insert(new Admin(null,"loginAcct"+i,"userPswd"+i,"userName"+i,"132391"+i+"@qq.com",null));
        }
    }
}

