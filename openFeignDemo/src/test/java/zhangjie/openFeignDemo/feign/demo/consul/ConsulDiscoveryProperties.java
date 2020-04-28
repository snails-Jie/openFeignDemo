package zhangjie.openFeignDemo.feign.demo.consul;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @autor zhangjie
 * @date 2020/4/13 15:43
 */
public class ConsulDiscoveryProperties {
    /**
     * serviceId的映射 --> 在服务器列表中查询的标签
     *  这允许通过一个标签来过滤服务
     */
    private Map<String, String> serverListQueryTags = new HashMap<>();
    /** 如果serverListQueyTags中没有列出服务列表中要查询的标签 */
    private String defaultQueryTag;

    /**
     * 在/v1/health/service/serviceName 中添加'passing'参数
     * -->这将会把健康检查传递给服务器
     */
    private boolean queryPassing = false;

    /**
     * serviceId --> 服务器列表中要查询的数据中心
     *  这样可以查询其他数据中心的服务
     */
    private Map<String, String> datacenters = new HashMap<>();

    @Value("${consul.token:${CONSUL_TOKEN:${spring.cloud.consul.token:${SPRING_CLOUD_CONSUL_TOKEN:}}}}")
    private String aclToken;


    /**
     * 服务实例区域来自元数据
     * 这允许更改元数据标签的名称
     */
    private String defaultZoneMetadataName = "zone";

    /**
     * @param serviceId 查询的服务是谁的过滤标签
     * @return 给定的服务ID应该被过滤的标签，或者为空
     */
    public String getQueryTagForService(String serviceId){
        String tag = serverListQueryTags.get(serviceId);
        return tag != null ? tag : defaultQueryTag;
    }

    public boolean isQueryPassing() {
        return queryPassing;
    }

    public void setQueryPassing(boolean queryPassing) {
        this.queryPassing = queryPassing;
    }

    public Map<String, String> getDatacenters() {
        return datacenters;
    }

    public void setDatacenters(Map<String, String> datacenters) {
        this.datacenters = datacenters;
    }

    public String getAclToken() {
        return aclToken;
    }

    public void setAclToken(String aclToken) {
        this.aclToken = aclToken;
    }

    public String getDefaultZoneMetadataName() {
        return defaultZoneMetadataName;
    }

    public void setDefaultZoneMetadataName(String defaultZoneMetadataName) {
        this.defaultZoneMetadataName = defaultZoneMetadataName;
    }
}
