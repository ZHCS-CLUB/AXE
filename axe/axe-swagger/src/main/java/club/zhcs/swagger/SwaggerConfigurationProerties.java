package club.zhcs.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kerbores
 *
 */
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfigurationProerties {

	String pathMapping = "/";

	String title = "接口文档";

	String basePackage = "com.sino";

	String description = "接口手册";

	String version = "1.0";

	String termsOfServiceUrl = "http://www.sinosoft.com.cn";

	String contactName = "Kerbores";

	String contactUrl = "http://www.sinosoft.com.cn";

	String contactEmail = "wangguiyuan@sinosoft.com.cn";

	String license = "The Apache License, Version 2.0";

	String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.html";

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the pathMapping
	 */
	public String getPathMapping() {
		return pathMapping;
	}

	/**
	 * @param pathMapping
	 *            the pathMapping to set
	 */
	public void setPathMapping(String pathMapping) {
		this.pathMapping = pathMapping;
	}

	/**
	 * @return the basePackage
	 */
	public String getBasePackage() {
		return basePackage;
	}

	/**
	 * @param basePackage
	 *            the basePackage to set
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the termsOfServiceUrl
	 */
	public String getTermsOfServiceUrl() {
		return termsOfServiceUrl;
	}

	/**
	 * @param termsOfServiceUrl
	 *            the termsOfServiceUrl to set
	 */
	public void setTermsOfServiceUrl(String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactUrl
	 */
	public String getContactUrl() {
		return contactUrl;
	}

	/**
	 * @param contactUrl
	 *            the contactUrl to set
	 */
	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail
	 *            the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @param license
	 *            the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @return the licenseUrl
	 */
	public String getLicenseUrl() {
		return licenseUrl;
	}

	/**
	 * @param licenseUrl
	 *            the licenseUrl to set
	 */
	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

}
