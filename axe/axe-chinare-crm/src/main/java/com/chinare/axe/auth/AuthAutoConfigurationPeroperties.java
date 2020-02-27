package com.chinare.axe.auth;

import java.util.List;

import org.nutz.lang.Lang;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Data
@ConfigurationProperties("mdp.auth")
public class AuthAutoConfigurationPeroperties {
    /**
     * 不用鉴权的url正则表达式列表
     */
    List<String> withoutAuthenticationUrlRegulars = Lang.list();
}
