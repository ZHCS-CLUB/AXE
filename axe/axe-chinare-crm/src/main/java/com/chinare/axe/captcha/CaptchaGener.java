package com.chinare.axe.captcha;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public interface CaptchaGener {
    /**
     * 生成指定长度验证码
     * 
     * @param length
     *            长度
     * @return 验证码
     */
    String gen(int length);
}
