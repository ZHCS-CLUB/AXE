package com.chinare.axe.apm;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public interface URLProvider {
    /**
     * 获取当前请求url
     * 
     * @return 当前请求url
     */
    String provide();
}
