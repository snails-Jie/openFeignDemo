package zhangjie.openFeignDemo.feign.demo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @autor zhangjie
 * @date 2020/4/11 18:03
 */
@Data
@ToString
public class AdRegisterDTO {
    @NotNull(
            message = "uid不能为空"
    )
    private Long uid;
    @NotNull(
            message = "deviceToken不能为空"
    )
    private String deviceToken;
    @NotNull(
            message = "adId不能为空"
    )
    private String adId;
}
