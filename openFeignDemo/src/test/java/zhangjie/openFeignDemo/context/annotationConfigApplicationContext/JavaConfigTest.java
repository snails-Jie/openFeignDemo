package zhangjie.openFeignDemo.context.annotationConfigApplicationContext;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaConfigTest {

    @Test
    public void  testConfig(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        Entitlement ent = (Entitlement) ctx.getBean("entitlement");
        System.out.println(ent.getName());
        System.out.println(ent.getTime());

        ctx.close();

    }
}
