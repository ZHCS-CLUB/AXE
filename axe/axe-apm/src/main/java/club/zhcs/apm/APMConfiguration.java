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
	public APMInterceptor apmInterceptor(APMAppender appender, UserCollector collector, URLProvider urlProvider) {
		return new APMInterceptor(appender, collector, urlProvider);
	}

	@Bean
	@ConditionalOnMissingBean
	public APMAppender apmAppender() {
		return new APMAppender() {
			Log logger = Logs.get();

			@Override
			public void append(APMLog log) {
				logger.info(log);
			}
		};
	}

	@Bean
	@ConditionalOnMissingBean
	public URLProvider urlProvider() {
		return new URLProvider() {

			@Override
			public String provide() {
				return null;
			}
		};
	}

	@Bean
	@ConditionalOnMissingBean
	public UserCollector userCollector() {
		return new UserCollector() {

			@Override
			public String collector() {
				return null;
			}
		};
	}

}
