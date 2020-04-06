package zhangjie.openFeignDemo.register.importBeanDefinitionRegister;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import zhangjie.openFeignDemo.register.importSelector.MyImportSelector;

@Configuration
@Import({MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
public class ImportBeanDefnitionRegistrarMainConfig {
}
