package zhangjie.openFeignDemo.proxy;

/**
 * @autor zhangjie
 * @date 2020/4/7 17:39
 */
public class PersonImpl implements Person {
    @Override
    public void eat() {
        System.out.println("time to eat");
    }
}
