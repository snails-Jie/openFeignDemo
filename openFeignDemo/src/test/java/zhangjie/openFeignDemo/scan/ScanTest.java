package zhangjie.openFeignDemo.scan;

import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import zhangjie.openFeignDemo.scan.annotation.Consumer;

import java.util.Set;

public class ScanTest {

    @Test
    public void testClassPathScanningCandidateComponentProvider(){
        ClassPathScanningCandidateComponentProvider provider = getScanner();
        provider.addIncludeFilter(new AnnotationTypeFilter(Consumer.class));
        Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents("zhangjie.openFeignDemo.scan.bean");
        Assert.isTrue(beanDefinitionSet.size()==1,"只能扫描到ConsumerWithComponentAnnotation");
    }


    protected ClassPathScanningCandidateComponentProvider getScanner(){
        return new ClassPathScanningCandidateComponentProvider(false);
//        {
//            @Override
//            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//                boolean isCandidate = false;
//                if (beanDefinition.getMetadata().isIndependent()) {
//
//                    if (!beanDefinition.getMetadata().isAnnotation()) {
//                        isCandidate = true;
//                    }
//                }
//                return isCandidate;
//            }
//        };
    }

}
