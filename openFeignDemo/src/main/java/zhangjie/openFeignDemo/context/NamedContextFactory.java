package zhangjie.openFeignDemo.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 子容器
 */
public abstract class NamedContextFactory<C extends NamedContextFactory.Specification> implements ApplicationContextAware {

    //父容器
    private ApplicationContext parent;

    //子容器配置
    private Class<?> defaultConfigType;

    private Map<String,AnnotationConfigApplicationContext> contexts = new ConcurrentHashMap<>();


    public NamedContextFactory(Class<?> defaultConfigType){
        this.defaultConfigType = defaultConfigType;
    }

    @Override
    public void setApplicationContext(ApplicationContext parent) throws BeansException {
        this.parent = parent;
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
