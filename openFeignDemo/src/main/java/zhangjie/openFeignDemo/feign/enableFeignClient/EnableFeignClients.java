package zhangjie.openFeignDemo.feign.enableFeignClient;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignClientsRegistrar.class)
public @interface EnableFeignClients {

    String[] basePackages() default {};

    /**
     *  for instance {@link feign.codec.Decoder}
     * @see FeignClientsConfiguration ：默认配置
     * @return 默认配置集合
     */
    Class<?>[] defaultConfiguration() default {};
}
