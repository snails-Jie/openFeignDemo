package zhangjie.openFeignDemo.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class FeignAutoConfiguration {

    @Autowired(required = false)
    private List<FeignClientSpecification> configurations = new ArrayList<>();

    @Bean
    public FeignContext feignContext() {
        FeignContext context = new FeignContext();
        context.setConfigurations(this.configurations);
        return context;
    }
}
