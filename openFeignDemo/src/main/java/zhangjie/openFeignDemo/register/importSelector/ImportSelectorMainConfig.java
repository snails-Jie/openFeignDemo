package zhangjie.openFeignDemo.register.importSelector;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MyImportSelector.class})
public class ImportSelectorMainConfig {
}
