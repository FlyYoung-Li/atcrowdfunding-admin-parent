import com.crowd.entity.Admin;
import com.crowd.mapper.AdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 23:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class ConnectTest2 {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private DataSource dataSource;


    @Test
    public void test2(){
        Admin admin = adminMapper.selectByPrimaryKey(2);
        System.out.println(admin);
    }
    @Test
    public void test3() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

    }
}
