package com.chinare.axe.utils;

import java.io.File;
import java.util.Properties;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Files;
import org.nutz.lang.Streams;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Logs;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class PropertiesUtils {
    private PropertiesUtils() {}

    public static boolean saveOrUpdate(Properties properties, String path) {
        try {
            File f = Files.createFileIfNoExists(path);
            properties.store(Streams.fileOut(f), "edit/create at " + Times.format("yyyy-MM-dd HH mm:ss", Times.now()));
            return true;
        }
        catch (Exception e) {
            Logs.get().debug(e);
            return false;
        }
    }

    public static boolean merge(String path, NutMap data) {
        PropertiesProxy proxy = new PropertiesProxy(false, path);
        proxy.putAll(data);
        Properties properties = proxy.toProperties();
        return saveOrUpdate(properties, path);
    }

    public static boolean edit(String path, String key, String value) {
        PropertiesProxy proxy = new PropertiesProxy(false, path);
        Properties properties = proxy.toProperties();
        properties.put(key, value);
        return saveOrUpdate(properties, path);
    }
}
