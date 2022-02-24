import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import org.junit.jupiter.api.Test;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/9/6 16:46
 */
public class StringTest {
    @Test
    public void test01() {
        String pswd = "123123";
        String s = CrowdUtil.md5(pswd);
        System.out.println(s);
    }

    @Test
    public void sendShortMessageTest() {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "cba5876432a3491c892bcaa7138f0823";
        String phone_number = "13294542523";
        String template_id = "TPL_0001";
        ResultEntity<String> stringResultEntity = CrowdUtil.sendShortMessage(host, path, method, appcode, phone_number, template_id);
        System.out.println(stringResultEntity);
    }

    @Test
    public void splitTest() {
        String string = "/";
        String[] split = string.split("/");
        System.out.println("开始遍历");
        System.out.println("split.length:"+split.length);
    }
}
