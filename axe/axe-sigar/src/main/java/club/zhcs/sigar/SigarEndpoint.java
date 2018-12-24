package club.zhcs.sigar;

import org.hyperic.sigar.Sigar;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author 王贵源<kerbores@gmail.com>
 *
 * @date 2018-12-24 09:23:09
 */
@Endpoint(id = "sigar")
public class SigarEndpoint {

    @ReadOperation
    public Sigar sigar() {
        return SigarFactory.load();
    }
}
