package zhangjie.openFeignDemo.context.defineConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:jdbc.properties")
public class JdbcTest {
    @Value("${jdbc.url}")
    private String url;

    @Test
    public void valueTest(){
        System.out.println("url:"+url);
    }
}
