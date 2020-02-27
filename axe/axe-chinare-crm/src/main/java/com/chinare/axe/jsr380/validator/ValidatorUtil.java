package com.chinare.axe.jsr380.validator;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class ValidatorUtil {
    private ValidatorUtil() {}

    private static final Pattern MOBILE_PATTERN = Pattern
                                                         .compile("/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/");

    public static boolean isMobile(String mobile) {
        return MOBILE_PATTERN.matcher(Optional.ofNullable(mobile).orElse("")).matches();
    }

    /**
     * @param idcard
     *            身份证号码
     * @return 是否合法
     */
    public static boolean isIDCard(String idcard) {
        return IdcardUtils.isValidatedAllIdcard(idcard);
    }
}
