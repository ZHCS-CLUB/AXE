package com.chinare.axe.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 *
 *
 *
 */
public class Ips {

	private Ips() {
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
		} catch (Exception e) {
		}
		return null;
	}
}
