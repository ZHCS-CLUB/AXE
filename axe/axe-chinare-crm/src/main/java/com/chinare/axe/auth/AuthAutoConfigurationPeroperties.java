package com.chinare.axe.auth;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 *
 */
@ConfigurationProperties("auth")
public class AuthAutoConfigurationPeroperties {
	List<String> withoutAuthenticationUrlRegulars;

	/**
	 * @return the withoutAuthenticationUrlRegulars
	 */
	public List<String> getWithoutAuthenticationUrlRegulars() {
		return withoutAuthenticationUrlRegulars;
	}

	/**
	 * @param withoutAuthenticationUrlRegulars the withoutAuthenticationUrlRegulars
	 *                                         to set
	 */
	public void setWithoutAuthenticationUrlRegulars(List<String> withoutAuthenticationUrlRegulars) {
		this.withoutAuthenticationUrlRegulars = withoutAuthenticationUrlRegulars;
	}

}
