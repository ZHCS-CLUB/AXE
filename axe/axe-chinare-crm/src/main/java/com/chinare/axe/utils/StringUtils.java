package com.chinare.axe.utils;

import java.util.ArrayList;
import java.util.List;

import org.nutz.castor.Castors;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

/**
 * @author Ixion
 * 
 */
public class StringUtils {

	private StringUtils() {
	}

	/**
	 * 字符串去除空白字符后从左到右按照一定位数插入字符
	 * 
	 * @param str 源
	 * @param n   位数
	 * @param sp  插入字符
	 * @return 字符串
	 */
	public static String change(String str, int n, String sp) {
		StringBuilder info = new StringBuilder();
		String nstr = sTrim(str);
		for (int i = 0; i < nstr.length(); i++) {
			info.append(nstr.charAt(i));
			if (i > 0 && (i + 1) % n == 0) {
				info.append(sp);
			}
		}
		return info.toString();
	}

	public static String getString(Object obj) {
		return isNullOrEmpty(obj) ? "" : obj.toString();
	}

	public static boolean isNullOrEmpty(Object object) {
		return object == null || Strings.isBlank(object.toString());
	}

	/**
	 * 字符串去除空白字符后从右到左按照一定位数插入字符
	 * 
	 * @param str 源
	 * @param n   位数
	 * @param sp  插入字符
	 * @return 字符串
	 */
	public static String rChange(String str, int n, String sp) {
		StringBuilder info = new StringBuilder();
		String nstr = sTrim(str);
		for (int i = nstr.length() - 1; i >= 0; i--) {
			info.append(nstr.charAt(i));
			if ((nstr.length() - i) % n == 0) {
				info.append(sp);
			}
		}
		return info.toString();
	}

	/**
	 * 去除全部空白
	 * 
	 * @param in 输入字符串
	 * @return 字符串
	 */
	public static String sTrim(String in) {
		return in.replaceAll("\\s*", "");
	}

	/**
	 * 将数字格式字符串转换成list
	 * 
	 * @param source 源字符串，格式为1,2,3
	 * @return 数字数组
	 */
	public static List<Integer> stringConvertList(String source) {
		return stringConvertList(source, Integer.class);
	}

	public static <T> List<T> stringConvertList(String[] source, final Class<T> clazz) {
		final List<T> target = new ArrayList<>();
		Lang.each(source, (index, info, length) -> target.add(Castors.me().castTo(info, clazz)));
		return target;

	}

	public static <T> List<T> stringConvertList(String source, Class<T> clazz) {
		if (Strings.isBlank(source)) {
			return new ArrayList<>();
		}
		String[] infos = source.split(",");
		return stringConvertList(infos, clazz);
	}
}
