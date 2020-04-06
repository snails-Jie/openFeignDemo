package zhangjie.openFeignDemo.register.importBeanDefinitionRegister;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import zhangjie.openFeignDemo.register.Blue;
import zhangjie.openFeignDemo.register.Red;
import zhangjie.openFeignDemo.register.Yellow;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean definition = registry.containsBeanDefinition(Red.class.getName());
        boolean definition2 = registry.containsBeanDefinition(Blue.class.getName());
        if(definition && definition2){
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Yellow.class);
            registry.registerBeanDefinition(Yellow.class.getName(),beanDefinition);
        }
    }
}
