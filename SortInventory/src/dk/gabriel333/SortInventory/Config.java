package dk.gabriel333.SortInventory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.bukkit.util.config.Configuration;

public class Config extends Configuration {
	public static String CONFIG_FILE = "config.yml";

	public Config(File file) {
		super(file);
		if (file.exists()) {
			load();
		} else {
			loadFileFromJar(CONFIG_FILE);
		}
	}

	private static File loadFileFromJar(String filename) {
		File actual = new File(SortInventory.PLUGIN_FOLDER, filename);
		if (!actual.exists()) {
			InputStream input = Config.class.getResourceAsStream("/"
					+ filename);
			if (input != null) {
				FileOutputStream output = null;
				try {
					output = new FileOutputStream(actual);
					byte[] buf = new byte[8192];
					int length = 0;

					while ((length = input.read(buf)) > 0) {
						output.write(buf, 0, length);
					}
					Messages.showWarning("The file: " + filename
							+ " has been created. PLEASE RELOAD.");
				} catch (Exception e) {
					Messages.showStackTrace(e);
				} finally {
					try {
						input.close();
					} catch (Exception e) {
					}
					try {
						output.flush();
						output.close();
					} catch (Exception e) {
					}
				}
			}
		}
		return actual;
	}
	
	public static boolean isEnabled(String string) {
		if (SortInventory.config.getString(string) == null) {
			Messages.showWarning("Missing parameter in config.yml ("
					+ string + "). Refesh config.yml. Defaulting to False");
			return false;
		} else if (SortInventory.config.getString(string).equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}
}
