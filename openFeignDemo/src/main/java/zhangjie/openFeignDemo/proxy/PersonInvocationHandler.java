package zhangjie.openFeignDemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @autor zhangjie
 * @date 2020/4/7 17:39
 */
public class PersonInvocationHandler implements InvocationHandler {
    private Object obj;

    public PersonInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before time to eat");
        method.invoke(obj,args);
        System.out.println("after time to eat");
        return null;
    }
}
