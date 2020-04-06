package zhangjie.openFeignDemo.context.annotationConfigApplicationContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name="entitlement")
    public Entitlement entitlement() {
        Entitlement ent= new Entitlement();
        ent.setName("Entitlement");
        ent.setTime(1);
        return ent;
    }
}
