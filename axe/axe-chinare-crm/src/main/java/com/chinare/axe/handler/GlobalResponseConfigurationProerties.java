package com.chinare.axe.handler;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Data
@ConfigurationProperties("axe.global.response")
public class GlobalResponseConfigurationProerties {

    /**
     * 是否开启
     */
    private boolean enabled = false;

    /**
     * 忽略的路径正则
     */
    List<String> ignorePaths;

}
