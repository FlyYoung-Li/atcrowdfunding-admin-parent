import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-27 15:13
 **/
public class BCryptTest {
    @Test
    public void BCryptTest(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String beforeEncode = bCryptPasswordEncoder.encode("123");
        System.out.println(beforeEncode);
        System.out.println("*********************");
        String afterEncode = bCryptPasswordEncoder.encode("123");
        System.out.println(afterEncode);
        System.out.println("**********************");
        boolean matches = bCryptPasswordEncoder.matches( "123",beforeEncode);
        System.out.println("是否相等："+matches);

    }
}
