package zhangjie.openFeignDemo.context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContextTest.Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContextTest {

    @Autowired
    private FeignContext feignContext;

    @Test
    public void testContext(){
        System.out.println(1111);
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    protected static class Application {

        @Bean
        public FeignContext feignContext(){
            FeignContext context = new FeignContext();
            return context;
        }
    }
}
