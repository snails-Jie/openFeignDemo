package zhangjie.openFeignDemo.feign.demo.consul;

import com.ecwid.consul.v1.health.model.HealthService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor zhangjie
 * @date 2020/4/13 10:35
 */
public class ConsulServerUtils {

    private static final Log log = LogFactory.getLog(ConsulServerUtils.class);

    public static String findHost(HealthService healthService) {
        HealthService.Service service = healthService.getService();
        HealthService.Node node = healthService.getNode();

        if (StringUtils.hasText(service.getAddress())) {
            return fixIPv6Address(service.getAddress());
        } else if (StringUtils.hasText(node.getAddress())) {
            return fixIPv6Address(node.getAddress());
        }
        return node.getNode();
    }

    public static String fixIPv6Address(String address) {
        try {
            InetAddress inetAdr = InetAddress.getByName(address);
            if (inetAdr instanceof Inet6Address) {
                return "[" + inetAdr.getHostName() + "]";
            }
            return address;
        } catch (UnknownHostException e) {
            log.debug("Not InetAddress: " + address + " , resolved as is.");
            return address;
        }
    }


    public static Map<String, String> getMetadata(HealthService healthService) {
        return getMetadata(healthService.getService().getTags());
    }

    public static Map<String, String> getMetadata(List<String> tags) {
        LinkedHashMap<String, String> metadata = new LinkedHashMap<>();
        if (tags != null) {
            for (String tag : tags) {
                String[] parts = StringUtils.delimitedListToStringArray(tag, "=");
                switch (parts.length) {
                    case 0:
                        break;
                    case 1:
                        metadata.put(parts[0], parts[0]);
                        break;
                    case 2:
                        metadata.put(parts[0], parts[1]);
                        break;
                    default:
                        String[] end = Arrays.copyOfRange(parts, 1, parts.length);
                        metadata.put(parts[0], StringUtils.arrayToDelimitedString(end, "="));
                        break;
                }

            }
        }

        return metadata;
    }
}