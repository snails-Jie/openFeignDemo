package zhangjie.openFeignDemo.feign;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zhangjie.openFeignDemo.feign.api.TestClient;
import zhangjie.openFeignDemo.feign.enableFeignClient.EnableFeignClients;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignHttpClientTests.Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignHttpClientTests {

    @Autowired
    private TestClient testClient;

    @Test
    public void testSimpleType(){
        String hello = testClient.getHello();
        assertThat(hello).as("hello was null").isNotNull();
        assertThat(hello).as("first hello didn't match")
                .isEqualTo("hello world 1");
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    @EnableFeignClients(basePackages = {
            "zhangjie.openFeignDemo.feign.api"
    })
    @RestController
    protected static class Application{
        @RequestMapping(method = RequestMethod.GET, value = "/hello")
        public String getHello() {
            return "hello world 1";
        }
    }


//    @FeignClient(name ="localapp")
//    protected interface TestClient {
//        @RequestMapping(method = RequestMethod.GET, value = "/hello",
//                produces = MediaType.APPLICATION_JSON_VALUE)
//        String getHello();
//    }

}
