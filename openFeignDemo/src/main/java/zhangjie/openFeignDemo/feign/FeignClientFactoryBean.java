package zhangjie.openFeignDemo.feign;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
public class FeignClientFactoryBean implements FactoryBean<Object>, ApplicationContextAware {

    private Class<?> type;

    private ApplicationContext applicationContext;

    @Override
    public Object getObject() throws Exception {
        System.out.println("调用FeignClientFactoryBean的getObject()");
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =applicationContext;
    }
}
