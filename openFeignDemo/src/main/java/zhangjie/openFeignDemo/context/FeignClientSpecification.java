package zhangjie.openFeignDemo.context;

public class FeignClientSpecification implements NamedContextFactory.Specification {
    private String name;

    private Class<?>[] configuration;

    public FeignClientSpecification() {
    }

    public FeignClientSpecification(String name, Class<?>[] configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<?>[] getConfiguration() {
        return this.configuration;
    }
}
