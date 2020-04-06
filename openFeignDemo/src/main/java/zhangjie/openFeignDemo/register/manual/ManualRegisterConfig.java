package zhangjie.openFeignDemo.register.manual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualRegisterConfig {
    private String name;

    private Class<?>[] configuration;
}
