package zhangjie.openFeignDemo.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 子容器
 */
public abstract class NamedContextFactory<C extends NamedContextFactory.Specification> implements ApplicationContextAware {

    private final String propertySourceName;

    private final String propertyName;

    //父容器
    private ApplicationContext parent;

    //子容器配置
    private Class<?> defaultConfigType;

    private Map<String, C> configurations = new ConcurrentHashMap<>();

    private Map<String,AnnotationConfigApplicationContext> contexts = new ConcurrentHashMap<>();


    public NamedContextFactory(Class<?> defaultConfigType,String propertySourceName,String propertyName){
        this.defaultConfigType = defaultConfigType;
        this.propertySourceName = propertySourceName;
        this.propertyName = propertyName;
    }

    @Override
    public void setApplicationContext(ApplicationContext parent) throws BeansException {
        this.parent = parent;
    }

    public void setConfigurations(List<C> configurations) {
        for (C client : configurations) {
            this.configurations.put(client.getName(), client);
        }
    }

    public <T> T getInstance(String name, Class<T> type) {
        AnnotationConfigApplicationContext context = getContext(name);
        if (BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context,
                type).length > 0) {
            return context.getBean(type);
        }
        return null;
    }

    /**
     * 1. 使用Class<T> 泛型可以避免强转转换
     */
    public <T> T getInnstance(String name,Class<T> type){
        AnnotationConfigApplicationContext context = getContext(name);
        if(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context,type).length >0){
            return context.getBean(type);
        }
        return null;
    }

    /**
     * 1.@FeignClient的name 与AnnotationConfigApplicationContext形成映射
     *  1.1 context注册配置
     *  1.2 注册属性 key = feign  value = Map(key=feign.client.name value=name)
     * @param name
     * @see zhangjie.openFeignDemo.feign.feignClient.FeignClient
     * @return
     */
    protected AnnotationConfigApplicationContext getContext(String name){
        if(!this.contexts.containsKey(name)){
            synchronized (this.contexts){
                if(!this.contexts.containsKey(name)){
                    this.contexts.put(name,createContext(name));
                }
            }
        }
        return this.contexts.get(name);
    }

    protected AnnotationConfigApplicationContext createContext(String name){
       AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
       context.register(PropertySourcesPlaceholderConfigurer.class,
               this.defaultConfigType);
       context.getEnvironment().getPropertySources().addFirst(new MapPropertySource(
               this.propertySourceName,
               Collections.singletonMap(this.propertyName,name)
       ));
       if(this.parent != null){
           context.setParent(parent);
           // jdk11 issue
           // https://github.com/spring-cloud/spring-cloud-netflix/issues/3101
           context.setClassLoader(this.parent.getClassLoader());
       }
       context.refresh();
       return context;
    }





    /**
     * 规范名称和配置
     */
    public interface Specification{
        String getName();

        Class<?>[] getConfiguration();
    }
}
