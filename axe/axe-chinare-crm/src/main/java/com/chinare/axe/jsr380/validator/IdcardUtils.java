package com.chinare.axe.jsr380.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.regex.Pattern;

import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class IdcardUtils {

    /**
     * 
     */
    private IdcardUtils() {}

    /**
     ** 省，直辖市代码表： { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",*
     * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",*
     * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",*
     * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",*
     * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",*
     * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
     */
    protected static String[][] codeAndCity = {{"11", "北京"},
                                               {"12", "天津"},
                                               {"13", "河北"},
                                               {"14", "山西"},
                                               {"15", "内蒙古"},
                                               {"21", "辽宁"},
                                               {"22", "吉林"},
                                               {"23", "黑龙江"},
                                               {"31", "上海"},
                                               {"32", "江苏"},
                                               {"33", "浙江"},
                                               {"34", "安徽"},
                                               {"35", "福建"},
                                               {"36", "江西"},
                                               {"37", "山东"},
                                               {"41", "河南"},
                                               {"42", "湖北"},
                                               {"43", "湖南"},
                                               {"44", "广东"},
                                               {"45", "广西"},
                                               {"46", "海南"},
                                               {"50", "重庆"},
                                               {"51", "四川"},
                                               {"52", "贵州"},
                                               {"53", "云南"},
                                               {"54", "西藏"},
                                               {"61", "陕西"},
                                               {"62", "甘肃"},
                                               {"63", "青海"},
                                               {"64", "宁夏"},
                                               {"65", "新疆"},
                                               {"71", "台湾"},
                                               {"81", "香港"},
                                               {"82", "澳门"},
                                               {"91", "国外"}};

    private static String[] cityCode = {"11",
                                        "12",
                                        "13",
                                        "14",
                                        "15",
                                        "21",
                                        "22",
                                        "23",
                                        "31",
                                        "32",
                                        "33",
                                        "34",
                                        "35",
                                        "36",
                                        "37",
                                        "41",
                                        "42",
                                        "43",
                                        "44",
                                        "45",
                                        "46",
                                        "50",
                                        "51",
                                        "52",
                                        "53",
                                        "54",
                                        "61",
                                        "62",
                                        "63",
                                        "64",
                                        "65",
                                        "71",
                                        "81",
                                        "82",
                                        "91"};

    private static int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private static Log logger = Logs.get();

    public static boolean isValidatedAllIdcard(String idcard) {
        if (idcard.length() == 15) {
            idcard = convertIdcarBy15bit(idcard);
        }
        return isValidate18Idcard(Optional.ofNullable(idcard).orElse(""));
    }

    public static boolean isValidate18Idcard(String idcard) {
        // 非18位为假
        if (idcard.length() != 18) {
            return false;
        }
        // 获取前17位
        String idcard17 = idcard.substring(0, 17);
        // 获取第18位
        String idcard18Code = idcard.substring(17, 18);
        char[] c = null;
        String checkCode = "";
        // 是否都为数字
        if (isDigital(idcard17)) {
            c = idcard17.toCharArray();
        } else {
            return false;
        }

        if (null != c) {
            int[] bit = converCharToInt(c);
            int sum17 = 0;
            sum17 = getPowerSum(bit);

            // 将和值与11取模得到余数进行校验码判断
            checkCode = getCheckCodeBySum(sum17);
            if (null == checkCode) {
                return false;
            }
            // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
            if (!idcard18Code.equalsIgnoreCase(checkCode)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidate15Idcard(String idcard) {
        if (idcard.length() == 15 && isDigital(idcard)) {
            String provinceid = idcard.substring(0, 2);
            String birthday = idcard.substring(6, 12);
            int year = Integer.parseInt(idcard.substring(6, 8));
            int month = Integer.parseInt(idcard.substring(8, 10));
            int day = Integer.parseInt(idcard.substring(10, 12));
            Date birthdate;
            try {
                birthdate = Times.parse("yyMMdd", birthday);
            }
            catch (ParseException e) {
                logger.debug(e);
                return false;
            }
            // 判断是否为合法的省份
            if (!isProvinceAv(provinceid)) {
                return false;
            }
            if (!isBirthDayAv(birthdate)) {
                return false;
            }
            // 该身份证生出日期在当前日期之后时为假

            // 判断是否为合法的年份
            GregorianCalendar curDay = new GregorianCalendar();
            // 判断是否为合法的日期
            if (!isYearAndMonthAv(year, month, curDay) || !isDayAv(month, day, birthdate, curDay)) {
                return false;
            }
        }
        return false;
    }

    /**
     * @param year
     * @param month
     * @param curDay
     */
    private static boolean isYearAndMonthAv(int year, int month, GregorianCalendar curDay) {
        int curYear = curDay.get(Calendar.YEAR);
        int year2bit = Integer.parseInt(String.valueOf(curYear)
                                              .substring(2));

        // 判断该年份的两位表示法，小于50的和大于当前年份的，为假
        if ((year < 50 && year > year2bit)) {
            return false;
        }
        // 判断是否为合法的月份
        return month >= 1 && month <= 12;
    }

    /**
     * @param month
     * @param day
     * @param birthdate
     * @param curDay
     * @return
     */
    private static boolean isDayAv(int month, int day, Date birthdate, GregorianCalendar curDay) {
        boolean mflag;
        curDay.setTime(birthdate); // 将该身份证的出生日期赋于对象curDay
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            mflag = (day >= 1 && day <= 31);
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            mflag = (day >= 1 && day <= 30);
        } else {
            if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
                mflag = (day >= 1 && day <= 29);
            } else {
                mflag = (day >= 1 && day <= 28);
            }
        }
        return mflag;
    }

    /**
     * @param birthday
     * @return
     */
    private static boolean isBirthDayAv(Date birthday) {
        return birthday != null && birthday.before(Times.now());
    }

    /**
     * @param provinceid
     * @param flag
     * @return
     */
    private static boolean isProvinceAv(String provinceid) {
        for (String id : cityCode) {
            if (id.equals(provinceid)) {
                return true;
            }
        }
        return false;
    }

    public static String convertIdcarBy15bit(String idcard) {
        String idcard17 = null;
        // 非15位身份证
        if (idcard.length() != 15) {
            return null;
        }

        if (isDigital(idcard)) {
            // 获取出生年月日
            String birthday = idcard.substring(6, 12);
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
            }
            catch (ParseException e) {
                logger.debug(e);
                return null;
            }
            Calendar cday = Calendar.getInstance();
            cday.setTime(birthdate);
            String year = String.valueOf(cday.get(Calendar.YEAR));

            idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

            char[] c = idcard17.toCharArray();
            String checkCode = "";

            if (null != c) {
                int[] bit = converCharToInt(c);
                int sum17 = 0;
                sum17 = getPowerSum(bit);
                // 获取和值与11取模得到余数进行校验码
                checkCode = getCheckCodeBySum(sum17);
                // 获取不到校验位
                if (null == checkCode) {
                    return null;
                }

                // 将前17位与第18位校验码拼接
                idcard17 += checkCode;
            }
        } else { // 身份证包含数字
            return null;
        }
        return idcard17;
    }

    public static boolean isIdcard(String idcard) {
        return !Strings.isBlank(idcard)
               && Pattern.matches(
                                  "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)",
                                  idcard);
    }

    public static boolean is15Idcard(String idcard) {
        return !Strings.isBlank(idcard)
               && Pattern.matches(
                                  "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",
                                  idcard);
    }

    public static boolean is18Idcard(String idcard) {
        return Pattern
                      .matches(
                               "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
                               idcard);
    }

    public static boolean isDigital(String str) {
        return !Strings.isBlank(str) && str.matches("^[0-9]*$");
    }

    public static int getPowerSum(int[] bit) {
        int sum = 0;
        if (power.length != bit.length) {
            return sum;
        }
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    public static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
        case 10:
            checkCode = "2";
            break;
        case 9:
            checkCode = "3";
            break;
        case 8:
            checkCode = "4";
            break;
        case 7:
            checkCode = "5";
            break;
        case 6:
            checkCode = "6";
            break;
        case 5:
            checkCode = "7";
            break;
        case 4:
            checkCode = "8";
            break;
        case 3:
            checkCode = "9";
            break;
        case 2:
            checkCode = "x";
            break;
        case 1:
            checkCode = "0";
            break;
        case 0:
            checkCode = "1";
            break;
        default:
            break;
        }
        return checkCode;
    }

    public static int[] converCharToInt(char[] c) {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }
}
