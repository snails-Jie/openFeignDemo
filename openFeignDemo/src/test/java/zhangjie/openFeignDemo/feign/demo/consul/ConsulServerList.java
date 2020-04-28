package zhangjie.openFeignDemo.feign.demo.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @autor zhangjie
 * @date 2020/4/13 10:26
 */
public class ConsulServerList extends AbstractServerList<ConsulServer> {

    private final ConsulClient client;
    private final ConsulDiscoveryProperties properties;

    private String serviceId;

    /**
     * 通过反射调用
     * {@link com.netflix.loadbalancer.DynamicServerListLoadBalancer#initWithNiwsConfig(IClientConfig)}
     */
    public ConsulServerList(){
        this.client = new ConsulClient("10.0.128.209",8500);
        this.properties = new ConsulDiscoveryProperties();
    }


    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        this.serviceId = clientConfig.getClientName();
    }

    @Override
    public List<ConsulServer> getInitialListOfServers() {
        return getServers();
    }

    @Override
    public List<ConsulServer> getUpdatedListOfServers() {
        return getServers();
    }

    private List<ConsulServer> getServers() {
        if (this.client == null) {
            return Collections.emptyList();
        }
        String tag = getTag(); // null is ok
        Response<List<HealthService>> response = this.client.getHealthServices(
                this.serviceId, tag, this.properties.isQueryPassing(),
                createQueryParamsForClientRequest(), this.properties.getAclToken());
        if (response.getValue() == null || response.getValue().isEmpty()) {
            return Collections.emptyList();
        }
        return transformResponse(response.getValue());
    }

    protected String getTag() {
        return this.properties.getQueryTagForService(this.serviceId);
    }

    /**
     * 该方法将创建{@link QueryParams}，
     * 在检索来自Consul的服务时使用{@link QueryParams}，默认情况下使用{@link QueryParams#DEFAULT}
     *   如果是指定了一个数据中心，当前serviceId {@link QueryParams#datacenter}被设置
     * @return 一个{@link QueryParams}的实例
     */
    protected QueryParams createQueryParamsForClientRequest() {
        String datacenter = getDatacenter();
        if (datacenter != null) {
            return new QueryParams(datacenter);
        }
        return QueryParams.DEFAULT;
    }

    protected String getDatacenter() {
        return this.properties.getDatacenters().get(this.serviceId);
    }

    protected List<ConsulServer> transformResponse(List<HealthService> healthServices) {
        List<ConsulServer> servers = new ArrayList<>();
        for (HealthService service : healthServices) {
            ConsulServer server = new ConsulServer(service);
            if (server.getMetadata().containsKey(this.properties.getDefaultZoneMetadataName())) {
                server.setZone(server.getMetadata().get(this.properties.getDefaultZoneMetadataName()));
            }
            servers.add(server);
        }
        return servers;
    }
}
