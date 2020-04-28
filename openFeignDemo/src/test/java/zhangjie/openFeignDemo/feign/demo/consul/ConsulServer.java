package zhangjie.openFeignDemo.feign.demo.consul;

import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import com.netflix.loadbalancer.Server;

import java.util.Map;

import static zhangjie.openFeignDemo.feign.demo.consul.ConsulServerUtils.findHost;

/**
 * @autor zhangjie
 * @date 2020/4/13 10:32
 */
public class ConsulServer extends Server {

    private final MetaInfo metaInfo;
    private final HealthService service;
    private final Map<String, String> metadata;

    public ConsulServer(final HealthService healthService) {
        super(findHost(healthService), healthService.getService().getPort());
        this.service = healthService;
        this.metadata = ConsulServerUtils.getMetadata(this.service);
        metaInfo = new MetaInfo() {
            @Override
            public String getAppName() {
                return service.getService().getService();
            }

            @Override
            public String getServerGroup() {
                return getMetadata().get("group");
            }

            @Override
            public String getServiceIdForDiscovery() {
                return null;
            }

            @Override
            public String getInstanceId() {
                return service.getService().getId();
            }
        };

        setAlive(isPassingChecks());
    }

    @Override
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public HealthService getHealthService() {
        return this.service;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public boolean isPassingChecks() {
        for (Check check : this.service.getChecks()) {
            if (check.getStatus() != Check.CheckStatus.PASSING) {
                return false;
            }
        }
        return true;
    }
}

