package zhangjie.openFeignDemo.context.defineConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试用例参考：https://blog.csdn.net/w605283073/article/details/80004768
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefineConfigFileTest.Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefineConfigFileTest {

    @Autowired
    private Environment env;

    @Value("${jdbc.url}")
    private String url;

    @Test
    public void testEnvironment(){
        System.out.println("url:"+url);
        System.out.println("jdbc.url:"+env.getProperty("jdbc.url"));
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    @PropertySource("classpath:jdbc.properties")
    protected static class Application{

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
            return new PropertySourcesPlaceholderConfigurer();
        }
    }




}
