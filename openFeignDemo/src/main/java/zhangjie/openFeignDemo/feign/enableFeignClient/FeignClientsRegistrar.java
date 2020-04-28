package zhangjie.openFeignDemo.feign.enableFeignClient;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import zhangjie.openFeignDemo.context.FeignClientSpecification;
import zhangjie.openFeignDemo.feign.FeignClientFactoryBean;
import zhangjie.openFeignDemo.feign.feignClient.FeignClient;

import java.util.Map;
import java.util.Set;

public class FeignClientsRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerDefaultConfiguration(metadata,registry);
        registerFeignClients(metadata,registry);
    }

    private void registerDefaultConfiguration(AnnotationMetadata metadata,
                                              BeanDefinitionRegistry registry) {
        System.out.println(metadata.getClassName());
        Map<String, Object> defaultAttrs = metadata
                .getAnnotationAttributes(EnableFeignClients.class.getName(), true);
        String name = "";
        if (defaultAttrs != null && defaultAttrs.containsKey("defaultConfiguration")) {
            //判断传入的defaultConfiguration的是不是topClass，所谓topClass就是说此类不是别的类的内部类

            name =  "default." + metadata.getClassName();;
        }

        //注册FeignClientSpecification
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(FeignClientSpecification.class);
//        builder.addConstructorArgValue(name);
//        builder.addConstructorArgValue(defaultAttrs.get("defaultConfiguration"));
        registry.registerBeanDefinition(name+"."+FeignClientSpecification.class.getSimpleName(),
                builder.getBeanDefinition());
    }

    public void registerFeignClients(AnnotationMetadata metadata,
                                     BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(FeignClient.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents("zhangjie.openFeignDemo.feign.api");
        for (BeanDefinition candidateComponent : candidateComponents){
            if(candidateComponent instanceof AnnotatedBeanDefinition){
                // verify annotated class is an interface
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata =  beanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(),"@FeignClient can only be specified on an interface");

                Map<String,Object> attributes = annotationMetadata.getAnnotationAttributes(FeignClient.class.getCanonicalName());
                registerFeignClient(registry, annotationMetadata, attributes);
            }
        }
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {

                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    private void registerFeignClient(BeanDefinitionRegistry registry,
                                     AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactoryBean.class);
        //校验属性：validate(attributes)
        String name = (String)attributes.get("name");
//        definition.addPropertyValue("name",name);
        definition.addPropertyValue("type",className);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        //从属性中得到contextId,如果没有取name属性值
        String contextId = name;
        String alias = contextId + "FeignClient";


        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition,className,new String[]{ alias });
        BeanDefinitionReaderUtils.registerBeanDefinition(holder,registry); //底层多了Name和别名的关联
    }



}
