package com.chinare.axe.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class FinanceUtils {
    private FinanceUtils() {}

    public static void m0(String sb, int type, StringBuilder sbf) {

        int len = sb.length();
        int b = 0;
        type++;
        if (len >= 4) {
            b = len - 4;
            sbf.insert(0, m1(sb.substring(b), type));
            m0(sb.substring(0, b), type, sbf);
        } else if (len > 0) {
            sbf.insert(0, m1(sb, type));
        }
        if ('零' == sbf.charAt(0)) {
            sbf.deleteCharAt(0);
        }
    }

    public static StringBuilder m1(String sb, int type) {

        StringBuilder sbf = new StringBuilder(sb);
        switch (type) {
        case 1:
            sbf.append("圆");
            break;
        case 2:
            sbf.append("万");
            break;
        case 3:
            sbf.append("亿");
            break;
        default:
            break;
        }
        // 开始赋值
        return m10(sbf);
    }

    private static StringBuilder m10(StringBuilder sbf) {
        int b = 0;
        char t = 0;
        for (int i = sbf.length() - 2; i >= 0; i--) {
            t = sbf.charAt(i);
            sbf.setCharAt(i, map.get(t));
            if (i != 0) {
                sbf.insert(i, mode[b]);
            }
            b++;
        }
        m11(sbf);
        if (sbf.length() == 1 && '圆' != sbf.charAt(0)) {
            sbf.setLength(0);
        }
        return sbf;
    }

    private static void m11(StringBuilder sbf) {
        char t;
        for (int i = 0; i < sbf.length(); i++) {
            t = sbf.charAt(i);
            if (t == '零') {
                t = sbf.charAt(i + 1);
                if ('圆' != t && '万' != t && '亿' != t) {
                    sbf.deleteCharAt(i + 1);
                } else {
                    sbf.deleteCharAt(i);
                }
                if (i != 0 && sbf.charAt(i - 1) == '零') {
                    sbf.deleteCharAt(i - 1);
                    i--;
                }
            }
        }
    }

    public static StringBuilder m2(String de) {

        if (de.length() > 2) {
            de = de.substring(0, 2);
        }
        de = de.replaceFirst("00", "");
        StringBuilder sb = new StringBuilder(de);
        if (sb.length() > 0) {
            if (sb.charAt(sb.length() - 1) == '0') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.setCharAt(0, map.get(sb.charAt(0)));
            switch (sb.length()) {
            case 1:
                sb.append("角");
                break;
            case 2:
                sb.setCharAt(1, map.get(sb.charAt(1)));
                if (sb.charAt(0) != '零') {
                    sb.insert(1, '角');
                }
                sb.append("分");
                break;
            default:
                break;
            }
        }
        return sb;
    }

    /**
     * 将字符串表示的金额转换成大写金额
     * 
     * @param in
     *            输入金额 字符串形式 如 12345.67
     * @return 大写金额 如 壹佰贰拾叁万肆仟伍佰陆拾陆圆贰角贰分 整
     */
    public static String toChar(String in) {

        StringBuilder sbf = new StringBuilder();
        String[] sp = in.split("\\.");
        if (sp.length == 2) {
            m0(sp[0], 0, sbf);
            sbf.append(m2(sp[1]));
        } else {
            m0(in, 0, sbf);
        }
        sbf.append(" 整");
        return sbf.toString();
    }

    // 缓存所有数字的
    private static Map<Character, Character> map = new HashMap<>(10);

    static {
        map.put('1', '壹');
        map.put('2', '贰');
        map.put('3', '叁');
        map.put('4', '肆');
        map.put('5', '伍');
        map.put('6', '陆');
        map.put('7', '柒');
        map.put('8', '捌');
        map.put('9', '玖');
        map.put('0', '零');
    }

    static char[] mode = new char[]{'拾', '佰', '仟'};
}
