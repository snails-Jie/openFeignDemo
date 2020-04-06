package zhangjie.openFeignDemo.autowired;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutowiredTest.Application.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class AutowiredTest {

    @Autowired
    private ClientTest clientTest;

    @Test
    public void testSimple(){
        System.out.println(1111);
    }

    /**
     * 注意 Application属于静态类
     */
    @Configuration
    @EnableAutoConfiguration
    @Import(BeanRegister.class)
    protected static  class Application{


    }

//    @Configuration
//    @EnableAutoConfiguration
//    protected static  class Application{
//
//        @Bean
//        public ClientTest clientTest(){
//            ClientTest clientTest = new ClientTest();
//            clientTest.setType(ClientTest.class);
//            return clientTest;
//        }
//
//    }



}
