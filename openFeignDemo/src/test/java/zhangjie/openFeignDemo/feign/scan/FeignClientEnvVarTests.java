package zhangjie.openFeignDemo.feign.scan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhangjie.openFeignDemo.feign.enableFeignClient.EnableFeignClients;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FeignClientEnvVarTests.Application.class,
                webEnvironment = RANDOM_PORT
)
public class FeignClientEnvVarTests {

    @Configuration
    @EnableAutoConfiguration
    @EnableFeignClients(basePackages = {
            "zhangjie.openFeignDemo.feign.api"
    })
    protected static class Application {

    }

    @Test
    public void testStart(){
        System.out.println("容器已启动");
    }
}
