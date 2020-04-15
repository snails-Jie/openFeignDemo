package zhangjie.openFeignDemo.feign.demo;

import com.ecwid.consul.v1.ConsulClient;
import com.netflix.client.ClientFactory;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import feign.ribbon.LBClient;
import feign.ribbon.LBClientFactory;
import feign.ribbon.RibbonClient;
import zhangjie.openFeignDemo.feign.demo.consul.ConsulDiscoveryProperties;
import zhangjie.openFeignDemo.feign.demo.consul.ConsulServerList;

/**
 *  用于创建LBClient {@link ConsulLBClientFactory#create(String)}
 *  1. Feign只提供client接口供于扩展 {@link LBClient#execute(LBClient.RibbonRequest, IClientConfig)}
 *  2. Factory注入 {@link RibbonClient.Builder#lbClientFactory(LBClientFactory)}
 * @autor zhangjie
 * @date 2020/4/13 10:18
 */
public class ConsulLBClientFactory implements LBClientFactory {

        public ConsulLBClientFactory() {

        }

    /**
     *  在初始化DynamicServerListLoadBalancer时会初始化allServerList
     * @param clientName
     * @return
     */
    public LBClient create(String clientName) {
            DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(clientName);
            config.set(CommonClientConfigKey.NIWSServerListClassName, ConsulServerList.class.getName());
            config.set(CommonClientConfigKey.NFLoadBalancerClassName, DynamicServerListLoadBalancer.class.getName());
            config.set(CommonClientConfigKey.ServerListRefreshInterval, 50);
            DynamicServerListLoadBalancer<Server> lb = new DynamicServerListLoadBalancer<Server>(config);
            return LBClient.create(lb, config);
        }

}
