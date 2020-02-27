package com.chinare.axe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class FileZipUtils {
    static final Log logger = Logs.get();

    /**
     * 
     */
    private FileZipUtils() {}

    private static void zipFile(ZipOutputStream zipOutputStream, File file, String parentFileName) {
        try (FileInputStream in = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(parentFileName);
            zipOutputStream.putNextEntry(zipEntry);
            int len;
            byte[] buf = new byte[8 * 1024];
            while ((len = in.read(buf)) != -1) {
                zipOutputStream.write(buf, 0, len);
            }
            zipOutputStream.closeEntry();
        }
        catch (Exception e) {
            logger.debug(e);
        }
    }

    /**
     * 递归压缩目录结构
     * 
     * @param zipOutputStream
     * @param file
     * @param parentFileName
     */
    private static void directory(ZipOutputStream zipOutputStream, File file, String parentFileName) {
        File[] files = file.listFiles();
        String parentFileNameTemp = null;
        for (File fileTemp : files) {
            parentFileNameTemp = Strings.isEmpty(parentFileName) ? fileTemp.getName()
                                                                 : parentFileName + File.separatorChar + fileTemp.getName();
            if (fileTemp.isDirectory()) {
                directory(zipOutputStream, fileTemp, parentFileNameTemp);
            } else {
                zipFile(zipOutputStream, fileTemp, parentFileNameTemp);
            }
        }
    }

    /**
     * 压缩文件目录
     * 
     * @param file
     *            源文件目录（单个文件和多层目录）
     * @param destit
     *            目标文件
     */
    public static void zipFiles(File file, String destit) {
        zipFiles(file, Files.createFileIfNoExists2(destit));
    }

    /**
     * 压缩文件目录
     * 
     * @param file
     *            源文件目录（单个文件和多层目录）
     * @param destit
     *            目标文件
     */
    public static void zipFiles(File file, File destit) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(destit);
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            if (file.isDirectory()) {
                directory(zipOutputStream, file, "");
            } else {
                zipFile(zipOutputStream, file, "");
            }
        }
        catch (Exception e) {
            logger.debug(e);
        }
    }
}
