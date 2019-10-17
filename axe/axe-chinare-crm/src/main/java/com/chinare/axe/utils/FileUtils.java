package com.chinare.axe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author 贵源 <br>
 *         create at 2014年5月10日
 */
public class FileUtils {

	private FileUtils() {
	}

	/**
	 * 清除SVN
	 * 
	 * @param dir 待清除的目录
	 * @return 清除成功状态标识
	 */
	public static boolean cleanSvn(File dir) {
		try {
			Files.cleanAllFolderInSubFolderes(dir, ".svn");
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 统计文件或者目录下的java代码的行数
	 * 
	 * @param file 文件或者目录
	 * @return java代码行数
	 */
	public static long countJavaCodeLines(File file) {
		return countLines(file, ".java");
	}

	public static double getDirSize(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
				{size += getDirSize(f);}
				return size;
			} else {
				return file.length();
			}
		} else {
			return 0.0;
		}
	}

	/**
	 * 转换文件大小
	 * 
	 * @param length 文件长度
	 * @return 文件大小字串描述
	 */
	public static String formetFileSize(double length) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (length < 1024) {
			fileSizeString = df.format(length) + "B";
		} else if (length < 1048576) {
			fileSizeString = df.format(length / 1024) + "K";
		} else if (length < 1073741824) {
			fileSizeString = df.format(length / 1048576) + "M";
		} else {
			fileSizeString = df.format(length / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 计算文件行数
	 * 
	 * @param file 文件(非目录类型)
	 * @return 行数
	 */
	public static long countLine(File file) {
		long target = 0;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				log.debug(line);
				target++;
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return target;
	}

	/**
	 * 统计文件或者目录下的指定类型文件的行数
	 * 
	 * @param file 文件或者目录
	 * @param suf  扩展名
	 * @return 行数
	 */
	public static long countLines(File file, String suf) {
		long target = 0;
		if (file.isFile() && file.getName().endsWith(suf)) {
			return countLine(file);
		} else if (file.isFile()) {
			return 0;
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				target += countLines(f, suf);
			}
		}
		return target;
	}

	/**
	 * 快速查找
	 * 
	 * @param dir  基目录
	 * @param name 待查找文件名
	 * @return 文件
	 */
	public static File fastFindFile(File dir, String name) {
		return fastFindFile(dir, name, 1);
	}

	/**
	 * 快速查找文件
	 * 
	 * @param dir    基目录
	 * @param name   文件名
	 * @param method 查找方法 1 全等查询 2 模糊查找 3 忽略大小写全等 4忽略大小写模糊
	 * @return 文件
	 */
	public static File fastFindFile(File dir, String name, int method) {
		File target = null;
		File[] dirs = Files.dirs(dir);// 获取目录
		File[] files = Files.files(dir, name);// 获取文件
		// 优先扫描文件
		if (files != null) {
			for (File file : files) {
				if (matchs(file.getName(), name, method)) {
					return file;
				}
			}
		}
		// 然后扫目录
		if (dirs != null) {
			for (File file : dirs) {
				target = fastFindFile(file, name, method);
				if (target != null) {
					return target;
				}
			}
		}
		return target;
	}

	private static boolean matchs(String fileName, String name, int method) {
		switch (method) {
		case 1:
			return Strings.equals(fileName, name);
		case 2:
			return fileName.endsWith(name);
		case 3:
			return Strings.equals(fileName.toUpperCase(), name.toUpperCase());
		default:
			return fileName.toUpperCase().endsWith(name.toUpperCase());
		}
	}

	public static File fastFindFile(String dir, String name) {
		return fastFindFile(new File(dir), name, 1);
	}

	/**
	 * 快速查找
	 * 
	 * @param dir  基目录
	 * @param name 待查找文件名
	 * @return 文件
	 */
	public static File fastFindFileLikeName(File dir, String name) {
		return fastFindFile(dir, name, 2);
	}

	/**
	 * 基本实现 文件查找
	 * 
	 * @param dir  查找的开始位置
	 * @param name 查找的文件的名字
	 * @return 文件
	 */
	public static File findFile(File dir, String name) {
		File target = null;
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && Strings.equals(file.getName(), name)) {
					return file;
				} else if (file.isDirectory()) {
					target = findFile(file, name);
					if (target != null) {
						return target;
					}
				}
			}
		}
		return target;
	}

	private static final Log log = Logs.get();
}
