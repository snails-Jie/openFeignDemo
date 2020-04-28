package zhangjie.openFeignDemo.feign.demo;

import feign.Feign;
import feign.RetryableException;
import feign.Retryer;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.ribbon.RibbonClient;
import feign.spring.SpringContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @autor zhangjie
 * @date 2020/4/11 11:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignDemo.Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignDemo {


    @Test
    public void testFeign(){
        AdRegisterDTO dto = new AdRegisterDTO();
        dto.setAdId("adasd12312");
        dto.setDeviceToken("asdasdas");
        dto.setUid(12312L);


        Class<UserServiceApi> type = UserServiceApi.class;
        String name = "southeast-userinfo";
        /**
         * @FeignClient(name = "southeast-userinfo", path = "/api/invoker/user")
         */
        String url = "http://southeast-adjust/api/invoker/adjust/register";
        Target<UserServiceApi> target = new Target.HardCodedTarget(type,name,url);

        RibbonClient ribbonClient = RibbonClient.builder().lbClientFactory(new ConsulLBClientFactory()).build();
        Feign.Builder build = Feign.builder()
                .retryer(NEVER_RETRY)
                .contract(new SpringContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .client(ribbonClient);

        UserServiceApi userServiceApi = build.target(target);

//        userServiceApi.ad(dto);
    }






    Retryer NEVER_RETRY = new Retryer() {

        @Override
        public void continueOrPropagate(RetryableException e) {
            throw e;
        }

        @Override
        public Retryer clone() {
            return this;
        }
    };

    @Configuration
    @EnableAutoConfiguration
    protected static  class Application{

    }



    interface UserServiceApi {
//        @PostMapping(value ={ "/ad"}, consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
//        ResultApi<?> ad(@RequestBody AdRegisterDTO var1);
    }



}
