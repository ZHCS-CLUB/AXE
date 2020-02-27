package com.chinare.axe.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class Ips {

    private static final int INADDR4SZ = 4;

    private Ips() {}

    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr);
    }

    public static byte[] textToNumericFormatV4(String src) {
        final byte[] nullbyte = new byte[0];
        if (src.length() == 0) {
            return nullbyte;
        }
        byte[] res = new byte[INADDR4SZ];
        String[] s = src.split("\\.", -1);
        try {
            switch (s.length) {
            case 1:
                return case1(nullbyte, res, s);
            case 2:
                return case2(nullbyte, res, s);
            case 3:
                return case3(nullbyte, res, s);
            case 4:
                return case4(nullbyte, res, s);
            default:
                return nullbyte;
            }
        }
        catch (NumberFormatException e) {
            return nullbyte;
        }
    }

    /**
     * @param nullbyte
     * @param res
     * @param s
     * @return
     */
    private static byte[] case4(final byte[] nullbyte, byte[] res, String[] s) {
        long val;
        for (int i = 0; i < 4; i++) {
            val = Integer.parseInt(s[i]);
            if (val < 0 || val > 0xff)
            {
                return nullbyte;
            }
            res[i] = (byte) (val & 0xff);
        }
        return res;
    }

    /**
     * @param nullbyte
     * @param res
     * @param s
     * @return
     */
    private static byte[] case3(final byte[] nullbyte, byte[] res, String[] s) {
        long val;
        for (int i = 0; i < 2; i++) {
            val = Integer.parseInt(s[i]);
            if (val < 0 || val > 0xff)
            {
                return nullbyte;
            }
            res[i] = (byte) (val & 0xff);
        }
        val = Integer.parseInt(s[2]);
        if (val < 0 || val > 0xffff)
        {
            return nullbyte;
        }
        res[2] = (byte) ((val >> 8) & 0xff);
        res[3] = (byte) (val & 0xff);
        return res;
    }

    /**
     * @param nullbyte
     * @param res
     * @param s
     * @return
     */
    private static byte[] case2(final byte[] nullbyte, byte[] res, String[] s) {
        long val;
        val = Integer.parseInt(s[0]);
        if (val < 0 || val > 0xff)
        {
            return nullbyte;
        }
        res[0] = (byte) (val & 0xff);
        val = Integer.parseInt(s[1]);
        if (val < 0 || val > 0xffffff)
        {
            return nullbyte;
        }
        res[1] = (byte) ((val >> 16) & 0xff);
        res[2] = (byte) (((val & 0xffff) >> 8) & 0xff);
        res[3] = (byte) (val & 0xff);
        return res;
    }

    /**
     * @param nullbyte
     * @param res
     * @param s
     * @return
     */
    private static byte[] case1(final byte[] nullbyte, byte[] res, String[] s) {
        long val;
        val = Long.parseLong(s[0]);
        if (val < 0 || val > 0xffffffffL)
        {
            return nullbyte;
        }
        res[0] = (byte) ((val >> 24) & 0xff);
        res[1] = (byte) (((val & 0xffffff) >> 16) & 0xff);
        res[2] = (byte) (((val & 0xffff) >> 8) & 0xff);
        res[3] = (byte) (val & 0xff);
        return res;
    }

    public static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
        case SECTION_1:
            return true;
        case SECTION_2:
            return b1 >= SECTION_3 && b1 <= SECTION_4;
        case SECTION_5:
            return b1 == SECTION_6;
        default:
            return false;
        }
    }

    public static String hostIp() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address) {
                        return ip.getHostAddress();
                    }
                }
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }
}
