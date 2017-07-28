package club.zhcs.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author kerbores
 *
 */
@Configuration
public class ValidationAutoConfiguration {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Bean
	public ValidationExceptionHandler validationExceptionHandler() {
		return new ValidationExceptionHandler();
	}

}