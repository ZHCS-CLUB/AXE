package club.zhcs.sigar;

import org.hyperic.sigar.Sigar;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author 王贵源 kerbores@gmail.com
 *
 */
@Endpoint(id = "sigar")
public class SigarEndpoint {

    @ReadOperation
    public Sigar sigar() {
        return SigarFactory.load();
    }
}
