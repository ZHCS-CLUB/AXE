package club.zhcs.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Sets;

import io.swagger.models.Swagger;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author kerbores
 *
 */
@Configuration
@EnableSwagger2
@ConditionalOnClass(Swagger.class)
@EnableConfigurationProperties(SwaggerConfigurationProerties.class)
public class SwaggerAutoConfiguration {

	@Autowired
	private SwaggerConfigurationProerties swaggerConfigurationProerties;

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.produces(Sets.newHashSet("application/json"))
				.consumes(Sets.newHashSet("application/json"))
				.protocols(Sets.newHashSet("http", "https"))
				.pathMapping(swaggerConfigurationProerties.getPathMapping())
				.select()
				.apis(RequestHandlerSelectors.basePackage(swaggerConfigurationProerties.getBasePackage()))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(swaggerConfigurationProerties.getTitle())
				.description(swaggerConfigurationProerties.getDescription())// 详细描述
				.version(swaggerConfigurationProerties.getVersion())// 版本
				.termsOfServiceUrl(swaggerConfigurationProerties.getTermsOfServiceUrl())
				.contact(new Contact(
						swaggerConfigurationProerties.getContactName(),
						swaggerConfigurationProerties.getContactUrl(),
						swaggerConfigurationProerties.getContactEmail()))// 作者
				.license(swaggerConfigurationProerties.getLicense())
				.licenseUrl(swaggerConfigurationProerties.getLicenseUrl())
				.build();
	}
}
