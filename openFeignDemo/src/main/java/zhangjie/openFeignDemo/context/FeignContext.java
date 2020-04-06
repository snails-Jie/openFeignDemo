package zhangjie.openFeignDemo.context;

/**
 * 一个创建feign类实例的工厂
 *  1.它为每个客户端名称创建了一个Spring ApplicationContext,并从中提取它所需要的bean
 */
public class FeignContext extends NamedContextFactory<FeignClientSpecification> {

    public FeignContext() {
        super(FeignClientSpecification.class);
    }

}
