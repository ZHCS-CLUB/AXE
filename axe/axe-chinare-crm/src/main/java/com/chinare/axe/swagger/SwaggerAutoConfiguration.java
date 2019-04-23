package com.chinare.axe.swagger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.nutz.lang.Lang;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * @author kerbores
 *
 */
@Configuration
@Import({ Swagger2Configuration.class })
public class SwaggerAutoConfiguration implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Bean
	@ConditionalOnMissingBean
	public SwaggerConfigurationProerties swaggerConfigurationProerties() {
		return new SwaggerConfigurationProerties();
	}

	@Bean
	public UiConfiguration uiConfiguration(SwaggerConfigurationProerties swaggerConfigurationProerties) {
		return UiConfigurationBuilder.builder()
				.deepLinking(swaggerConfigurationProerties.getUiConfig().getDeepLinking())
				.defaultModelExpandDepth(swaggerConfigurationProerties.getUiConfig().getDefaultModelExpandDepth())
				.defaultModelRendering(swaggerConfigurationProerties.getUiConfig().getDefaultModelRendering())
				.defaultModelsExpandDepth(swaggerConfigurationProerties.getUiConfig().getDefaultModelsExpandDepth())
				.displayOperationId(swaggerConfigurationProerties.getUiConfig().getDisplayOperationId())
				.displayRequestDuration(swaggerConfigurationProerties.getUiConfig().getDisplayRequestDuration())
				.docExpansion(swaggerConfigurationProerties.getUiConfig().getDocExpansion())
				.maxDisplayedTags(swaggerConfigurationProerties.getUiConfig().getMaxDisplayedTags())
				.operationsSorter(swaggerConfigurationProerties.getUiConfig().getOperationsSorter())
				.showExtensions(swaggerConfigurationProerties.getUiConfig().getShowExtensions())
				.tagsSorter(swaggerConfigurationProerties.getUiConfig().getTagsSorter())
				.validatorUrl(swaggerConfigurationProerties.getUiConfig().getValidatorUrl()).build();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(UiConfiguration.class)
	@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
	public List<Docket> createRestApi(SwaggerConfigurationProerties swaggerConfigurationProerties) {
		ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
		List<Docket> docketList = Lists.newArrayList();

		// 没有分组
		if (swaggerConfigurationProerties.getDocket().size() == 0) {
			return createRestApiWithoutDocket(swaggerConfigurationProerties, configurableBeanFactory, docketList);
		}

		// 分组创建
		return createRestApiWithDocketInfo(swaggerConfigurationProerties, configurableBeanFactory, docketList);
	}

	private List<Docket> createRestApiWithDocketInfo(SwaggerConfigurationProerties swaggerConfigurationProerties,
			ConfigurableBeanFactory configurableBeanFactory, List<Docket> docketList) {
		for (String groupName : swaggerConfigurationProerties.getDocket().keySet()) {
			SwaggerConfigurationProerties.DocketInfo docketInfo = swaggerConfigurationProerties.getDocket()
					.get(groupName);

			ApiInfo apiInfo = createApiInfo(swaggerConfigurationProerties, docketInfo);

			// base-path处理
			// 当没有配置任何path的时候，解析/**
			if (docketInfo.getBasePath().isEmpty()) {
				docketInfo.getBasePath().add("/**");
			}
			List<Predicate<String>> basePath = new ArrayList();
			for (String path : docketInfo.getBasePath()) {
				basePath.add(PathSelectors.ant(path));
			}

			// exclude-path处理
			List<Predicate<String>> excludePath = new ArrayList();
			for (String path : docketInfo.getExcludePath()) {
				excludePath.add(PathSelectors.ant(path));
			}

			Docket docketForBuilder = new Docket(DocumentationType.SWAGGER_2)
					.host(swaggerConfigurationProerties.getHost()).apiInfo(apiInfo)
					.securitySchemes(Collections.singletonList(apiKey()))
					.securityContexts(Collections.singletonList(securityContext()))
					.globalOperationParameters(assemblyGlobalOperationParameters(
							swaggerConfigurationProerties.getGlobalOperationParameters(),
							docketInfo.getGlobalOperationParameters()));

			// 全局响应消息
			if (!swaggerConfigurationProerties.getApplyDefaultResponseMessages()) {
				buildGlobalResponseMessage(swaggerConfigurationProerties, docketForBuilder);
			}

			Docket docket = docketForBuilder.groupName(groupName).select()
					.apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
					.paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath))).build();

			/* ignoredParameterTypes **/
			Class<?>[] array = new Class[docketInfo.getIgnoredParameterTypes().size()];
			Class<?>[] ignoredParameterTypes = docketInfo.getIgnoredParameterTypes().toArray(array);
			docket.ignoredParameterTypes(ignoredParameterTypes);

			configurableBeanFactory.registerSingleton(groupName, docket);
			docketList.add(docket);
		}
		return docketList;
	}

	private ApiInfo createApiInfo(SwaggerConfigurationProerties swaggerConfigurationProerties,
			SwaggerConfigurationProerties.DocketInfo docketInfo) {
		return new ApiInfoBuilder()
				.title(docketInfo.getTitle().isEmpty() ? swaggerConfigurationProerties.getTitle()
						: docketInfo.getTitle())
				.description(docketInfo.getDescription().isEmpty() ? swaggerConfigurationProerties.getDescription()
						: docketInfo.getDescription())
				.version(docketInfo.getVersion().isEmpty() ? swaggerConfigurationProerties.getVersion()
						: docketInfo.getVersion())
				.license(docketInfo.getLicense().isEmpty() ? swaggerConfigurationProerties.getLicense()
						: docketInfo.getLicense())
				.licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swaggerConfigurationProerties.getLicenseUrl()
						: docketInfo.getLicenseUrl())
				.contact(new Contact(
						docketInfo.getContact().getName().isEmpty()
								? swaggerConfigurationProerties.getContact().getName()
								: docketInfo.getContact().getName(),
						docketInfo.getContact().getUrl().isEmpty() ? swaggerConfigurationProerties.getContact().getUrl()
								: docketInfo.getContact().getUrl(),
						docketInfo.getContact().getEmail().isEmpty()
								? swaggerConfigurationProerties.getContact().getEmail()
								: docketInfo.getContact().getEmail()))
				.termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty()
						? swaggerConfigurationProerties.getTermsOfServiceUrl()
						: docketInfo.getTermsOfServiceUrl())
				.build();
	}

	private List<Docket> createRestApiWithoutDocket(SwaggerConfigurationProerties swaggerConfigurationProerties,
			ConfigurableBeanFactory configurableBeanFactory, List<Docket> docketList) {
		ApiInfo apiInfo = new ApiInfoBuilder().title(swaggerConfigurationProerties.getTitle())
				.description(swaggerConfigurationProerties.getDescription())
				.version(swaggerConfigurationProerties.getVersion()).license(swaggerConfigurationProerties.getLicense())
				.licenseUrl(swaggerConfigurationProerties.getLicenseUrl())
				.contact(new Contact(swaggerConfigurationProerties.getContact().getName(),
						swaggerConfigurationProerties.getContact().getUrl(),
						swaggerConfigurationProerties.getContact().getEmail()))
				.termsOfServiceUrl(swaggerConfigurationProerties.getTermsOfServiceUrl()).build();

		// base-path处理
		// 当没有配置任何path的时候，解析/**
		if (swaggerConfigurationProerties.getBasePath().isEmpty()) {
			swaggerConfigurationProerties.getBasePath().add("/**");
		}
		List<Predicate<String>> basePath = new ArrayList();
		for (String path : swaggerConfigurationProerties.getBasePath()) {
			basePath.add(PathSelectors.ant(path));
		}

		// exclude-path处理
		List<Predicate<String>> excludePath = Lists.newArrayList();
		for (String path : swaggerConfigurationProerties.getExcludePath()) {
			excludePath.add(PathSelectors.ant(path));
		}

		Docket docketForBuilder = new Docket(DocumentationType.SWAGGER_2).host(swaggerConfigurationProerties.getHost())
				.apiInfo(apiInfo).securitySchemes(Collections.singletonList(apiKey()))
				.securityContexts(Collections.singletonList(securityContext()))
				.globalOperationParameters(buildGlobalOperationParametersFromSwaggerConfigurationProerties(
						swaggerConfigurationProerties.getGlobalOperationParameters()));

		// 全局响应消息
		if (!swaggerConfigurationProerties.getApplyDefaultResponseMessages()) {
			buildGlobalResponseMessage(swaggerConfigurationProerties, docketForBuilder);
		}

		Docket docket = docketForBuilder.select()
				.apis(RequestHandlerSelectors.basePackage(swaggerConfigurationProerties.getBasePackage()))
				.paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath))).build();

		/* ignoredParameterTypes **/
		Class<?>[] array = new Class[swaggerConfigurationProerties.getIgnoredParameterTypes().size()];
		Class<?>[] ignoredParameterTypes = swaggerConfigurationProerties.getIgnoredParameterTypes().toArray(array);
		docket.ignoredParameterTypes(ignoredParameterTypes);

		configurableBeanFactory.registerSingleton("defaultDocket", docket);
		docketList.add(docket);
		return docketList;
	}

	private ApiKey apiKey() {
		return new ApiKey(swaggerConfigurationProerties().getAuthorization().getName(),
				swaggerConfigurationProerties().getAuthorization().getKeyName(), ApiKeyVehicle.HEADER.getValue());
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(swaggerConfigurationProerties().getAuthorization().getAuthRegex()))
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(
				SecurityReference.builder().reference(swaggerConfigurationProerties().getAuthorization().getName())
						.scopes(authorizationScopes).build());
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	private List<Parameter> buildGlobalOperationParametersFromSwaggerConfigurationProerties(
			List<SwaggerConfigurationProerties.GlobalOperationParameter> globalOperationParameters) {
		List<Parameter> parameters = Lang.list();

		if (Objects.isNull(globalOperationParameters)) {
			return parameters;
		}
		for (SwaggerConfigurationProerties.GlobalOperationParameter globalOperationParameter : globalOperationParameters) {
			parameters.add(new ParameterBuilder().name(globalOperationParameter.getName())
					.description(globalOperationParameter.getDescription())
					.modelRef(new ModelRef(globalOperationParameter.getModelRef()))
					.parameterType(globalOperationParameter.getParameterType())
					.required(Boolean.parseBoolean(globalOperationParameter.getRequired())).build());
		}
		return parameters;
	}

	private List<Parameter> assemblyGlobalOperationParameters(
			List<SwaggerConfigurationProerties.GlobalOperationParameter> globalOperationParameters,
			List<SwaggerConfigurationProerties.GlobalOperationParameter> docketOperationParameters) {

		if (Objects.isNull(docketOperationParameters) || docketOperationParameters.isEmpty()) {
			return buildGlobalOperationParametersFromSwaggerConfigurationProerties(globalOperationParameters);
		}

		Set<String> docketNames = docketOperationParameters.stream()
				.map(SwaggerConfigurationProerties.GlobalOperationParameter::getName).collect(Collectors.toSet());

		List<SwaggerConfigurationProerties.GlobalOperationParameter> resultOperationParameters = Lang.list();

		if (Objects.nonNull(globalOperationParameters)) {
			for (SwaggerConfigurationProerties.GlobalOperationParameter parameter : globalOperationParameters) {
				if (!docketNames.contains(parameter.getName())) {
					resultOperationParameters.add(parameter);
				}
			}
		}

		resultOperationParameters.addAll(docketOperationParameters);
		return buildGlobalOperationParametersFromSwaggerConfigurationProerties(resultOperationParameters);
	}

	private void buildGlobalResponseMessage(SwaggerConfigurationProerties swaggerConfigurationProerties,
			Docket docketForBuilder) {

		SwaggerConfigurationProerties.GlobalResponseMessage globalResponseMessages = swaggerConfigurationProerties
				.getGlobalResponseMessage();

		/* POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE 响应消息体 **/
		List<ResponseMessage> postResponseMessages = getResponseMessageList(globalResponseMessages.getPost());
		List<ResponseMessage> getResponseMessages = getResponseMessageList(globalResponseMessages.getGet());
		List<ResponseMessage> putResponseMessages = getResponseMessageList(globalResponseMessages.getPut());
		List<ResponseMessage> patchResponseMessages = getResponseMessageList(globalResponseMessages.getPatch());
		List<ResponseMessage> deleteResponseMessages = getResponseMessageList(globalResponseMessages.getDelete());
		List<ResponseMessage> headResponseMessages = getResponseMessageList(globalResponseMessages.getHead());
		List<ResponseMessage> optionsResponseMessages = getResponseMessageList(globalResponseMessages.getOptions());
		List<ResponseMessage> trackResponseMessages = getResponseMessageList(globalResponseMessages.getTrace());

		docketForBuilder.useDefaultResponseMessages(swaggerConfigurationProerties.getApplyDefaultResponseMessages())
				.globalResponseMessage(RequestMethod.POST, postResponseMessages)
				.globalResponseMessage(RequestMethod.GET, getResponseMessages)
				.globalResponseMessage(RequestMethod.PUT, putResponseMessages)
				.globalResponseMessage(RequestMethod.PATCH, patchResponseMessages)
				.globalResponseMessage(RequestMethod.DELETE, deleteResponseMessages)
				.globalResponseMessage(RequestMethod.HEAD, headResponseMessages)
				.globalResponseMessage(RequestMethod.OPTIONS, optionsResponseMessages)
				.globalResponseMessage(RequestMethod.TRACE, trackResponseMessages);
	}

	private List<ResponseMessage> getResponseMessageList(
			List<SwaggerConfigurationProerties.GlobalResponseMessageBody> globalResponseMessageBodyList) {
		List<ResponseMessage> responseMessages = Lang.list();
		for (SwaggerConfigurationProerties.GlobalResponseMessageBody globalResponseMessageBody : globalResponseMessageBodyList) {
			ResponseMessageBuilder responseMessageBuilder = new ResponseMessageBuilder();
			responseMessageBuilder.code(globalResponseMessageBody.getCode())
					.message(globalResponseMessageBody.getMessage());

			if (!StringUtils.isEmpty(globalResponseMessageBody.getModelRef())) {
				responseMessageBuilder.responseModel(new ModelRef(globalResponseMessageBody.getModelRef()));
			}
			responseMessages.add(responseMessageBuilder.build());
		}

		return responseMessages;
	}
}
