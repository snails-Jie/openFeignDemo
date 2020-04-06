package zhangjie.openFeignDemo.autowired;

import org.springframework.beans.factory.FactoryBean;


public class ClientTest implements FactoryBean<Object> {

    private Class<?> type;

    @Override
    public Object getObject() throws Exception {
        System.out.println("调用ClientTest的getObject()方法");
        return new ClientTest();
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
