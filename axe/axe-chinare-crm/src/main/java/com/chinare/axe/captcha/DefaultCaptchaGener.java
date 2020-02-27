package com.chinare.axe.captcha;

import org.nutz.lang.random.R;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class DefaultCaptchaGener implements CaptchaGener {
    /**
     * 纯数字池
     */
    public static final String NUMBER_POOL = "0123456789";

    /**
     * 纯字母池
     */
    public static final String LETTER_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 数字字母混合池,去掉部分易混淆的值
     */
    public static final String DEFAULT_POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String pool;

    /**
     * 验证码池类型
     * 
     * @author 王贵源(kerbores@gmail.com)
     */
    public enum Type {
        DEFAULT, NUMBER, LETTER
    }

    public static DefaultCaptchaGener instance(Type type) {
        switch (type) {
        case NUMBER:
            return new DefaultCaptchaGener(NUMBER_POOL);
        case LETTER:
            return new DefaultCaptchaGener(LETTER_POOL);
        default:
            return new DefaultCaptchaGener();
        }
    }

    public DefaultCaptchaGener() {
        super();
        pool = DEFAULT_POOL;
    }

    public DefaultCaptchaGener(String pool) {
        super();
        this.pool = pool;
    }

    @Override
    public String gen(int length) {
        if (length <= 0) {
            return "";
        }
        char[] pools = pool.toCharArray();
        StringBuilder bld = new StringBuilder();

        while (bld.length() < length) {
            bld.append(pools[R.random(0, pools.length - 1)]);
        }
        return bld.toString();
    }

}
