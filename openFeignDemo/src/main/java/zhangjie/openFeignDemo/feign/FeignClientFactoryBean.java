package zhangjie.openFeignDemo.feign;

import feign.Feign;
import feign.Target.HardCodedTarget;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import zhangjie.openFeignDemo.context.FeignContext;
import zhangjie.openFeignDemo.feign.target.HystrixTargeter;

@Data
public class FeignClientFactoryBean implements FactoryBean<Object>, ApplicationContextAware {

    private Class<?> type;

    private ApplicationContext applicationContext;

    private String contextId;

    @Override
    public Object getObject() throws Exception {
        return getTarget();
    }

    <T> T getTarget(){
        //FeignContext 为每个微服务的子容器，比如“asetku-southeast-adjust” --> FeignContext
        FeignContext context = applicationContext.getBean(FeignContext.class);
        /**
         * 待处理
         * 1.设置logger、encoder、decoder、contract
         * 2.设置client
         * -->从子容器中获取
         */
        Feign.Builder builder = null;
        //从子容器中获取
        HystrixTargeter targeter = null;
        // 封装type、name（“asetku-southeast-adjust”）、url(http://asetku-southeast-adjust/api/invoker/adjust/register)
        HardCodedTarget<T> target = null;
        return targeter.target(this, builder, context, target);

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
