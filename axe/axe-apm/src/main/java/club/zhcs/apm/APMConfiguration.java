package club.zhcs.apm;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kerbores
 *
 */
@Configuration
public class APMConfiguration {

	@Bean
	public APMInterceptor apmInterceptor(APMAppender appender, UserCollector collector) {
		return new APMInterceptor(appender, collector);
	}

	@Bean
	@ConditionalOnMissingBean
	public APMAppender apmAppender() {
		return new APMAppender() {
			Log logger = Logs.get();

			public void append(APMLog log) {
				logger.info(log);
			}
		};
	}

	@Bean
	@ConditionalOnMissingBean
	public UserCollector userCollector() {
		return new UserCollector() {

			public String collector() {
				return null;
			}
		};
	}

}
