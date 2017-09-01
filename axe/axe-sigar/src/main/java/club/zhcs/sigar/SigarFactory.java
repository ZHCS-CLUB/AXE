package club.zhcs.sigar;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hyperic.sigar.Sigar;
import org.nutz.lang.Files;
import org.nutz.resource.NutResource;
import org.nutz.resource.Scans;

/**
 * @author kerbores
 *
 */
public class SigarFactory {

	final static String LIB_PATH = String.format("%s%s%s", System.getProperty("user.home"), File.separator, "sigar_lib");

	static {
		List<NutResource> resources = Scans.me().scan("sigar", ".(so|sl|dylib|dll|lib)$");
		Files.deleteDir(Files.checkFile(LIB_PATH));// 先清空
		for (NutResource resource : resources) {
			try {
				Files.write(String.format("%s/%s", LIB_PATH, resource.getName()), resource.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String path = System.getProperty("java.library.path");
		path += File.pathSeparator + LIB_PATH;
		System.setProperty("java.library.path", path);
	}

	public static Sigar load() {
		return new Sigar();
	}
}
