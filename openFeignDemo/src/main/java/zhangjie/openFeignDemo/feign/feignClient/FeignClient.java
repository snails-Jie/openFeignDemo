package zhangjie.openFeignDemo.feign.feignClient;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeignClient {

    String name() default "";
}
