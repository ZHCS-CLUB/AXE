package com.chinare.axe.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * @author 王贵源 wangguiyuan@sinosoft.com.cn
 *
 */
@Configuration
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
@Import({
         Swagger2DocumentationConfiguration.class,
         BeanValidatorPluginsConfiguration.class
})
public class Swagger2Configuration {}