package zhangjie.openFeignDemo.proxy;

import java.lang.reflect.Proxy;

/**
 * @autor zhangjie
 * @date 2020/4/7 17:41
 */
public class JdkTest {

    public static void main(String[] args) {
        PersonInvocationHandler personInvocationHandler = new PersonInvocationHandler(new PersonImpl());
        Person personProxy = (Person) Proxy.newProxyInstance(PersonImpl.class.getClassLoader(),PersonImpl.class.getInterfaces(),personInvocationHandler);
        personProxy.eat();
    }
}
