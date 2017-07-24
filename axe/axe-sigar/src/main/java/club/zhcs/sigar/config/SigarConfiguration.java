package club.zhcs.sigar.config;

import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import club.zhcs.sigar.SigarMetrics;

/**
 * @author kerbores
 *
 */

@Configuration
@AutoConfigureBefore(EndpointAutoConfiguration.class)
public class SigarConfiguration {

	@Bean
	public SigarMetrics sigarMetrics() {
		return new SigarMetrics();
	}

}
