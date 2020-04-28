package zhangjie.openFeignDemo.feign.target;

import feign.Feign;
import feign.Target;
import zhangjie.openFeignDemo.context.FeignContext;
import zhangjie.openFeignDemo.feign.FeignClientFactoryBean;

/**
 * @autor zhangjie
 * @date 2020/4/7 18:40
 */
public class HystrixTargeter implements Targeter {
    @Override
    public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context, Target.HardCodedTarget<T> target) {
        // 调用feign方法生成代理类
        return feign.target(target);
    }
}
