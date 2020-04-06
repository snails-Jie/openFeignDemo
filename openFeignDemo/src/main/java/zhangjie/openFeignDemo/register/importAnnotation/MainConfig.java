package zhangjie.openFeignDemo.register.importAnnotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import zhangjie.openFeignDemo.register.Blue;
import zhangjie.openFeignDemo.register.Red;

@Configuration
@Import({Blue.class, Red.class}) // @Import导入组件，id默认是组件的全类名
public class MainConfig {
}
