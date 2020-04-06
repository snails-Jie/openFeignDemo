package zhangjie.openFeignDemo.register;

import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;
import zhangjie.openFeignDemo.register.importAnnotation.MainConfig;
import zhangjie.openFeignDemo.register.importBeanDefinitionRegister.ImportBeanDefnitionRegistrarMainConfig;
import zhangjie.openFeignDemo.register.importSelector.ImportSelectorMainConfig;
import zhangjie.openFeignDemo.register.manual.ManualRegisterConfig;

/**
 * 注册组件测试
 */
public class TestRegister {


    /**
     * @Import(要导入到容器中的组件) ；容器就会自动注册这个组件
     */
    @Test
    public void testImport() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                MainConfig.class);
        printBeans(applicationContext);
    }

    /**
     * ImportSelector：返回需要导入的组件的全类名数组
     */
    @Test
    public void testImportSelector(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportSelectorMainConfig.class);
        printBeans(applicationContext);
    }


    /**
     *  ImportBeanDefinitionRegistrar:手动注册bean到容器中
     */
    @Test
    public void testImportBeanDefinitionRegistrar(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportBeanDefnitionRegistrarMainConfig.class);
        printBeans(applicationContext);
    }

    /**
     * 手动注册
     */
    @Test
    public void testManualRegistrar(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ImportBeanDefnitionRegistrarMainConfig.class);

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ManualRegisterConfig.class);
        String name = "testConfig";
        Object config = null;
        builder.addConstructorArgValue(name);
        builder.addConstructorArgValue(config);

        applicationContext.registerBeanDefinition(name,builder.getBeanDefinition());

        Assert.isTrue(applicationContext.getBeanDefinition(name).getBeanClassName().equals(ManualRegisterConfig.class.getName()),"ManualRegisterConfig没有注册成功");
    }



    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}
