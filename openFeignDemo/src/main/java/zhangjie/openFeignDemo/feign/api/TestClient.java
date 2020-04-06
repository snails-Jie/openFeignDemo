package zhangjie.openFeignDemo.feign.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zhangjie.openFeignDemo.feign.feignClient.FeignClient;

@FeignClient(name ="localapp")
public interface TestClient {
    @RequestMapping(method = RequestMethod.GET, value = "/hello",
            produces = MediaType.APPLICATION_JSON_VALUE)
    String getHello();
}
