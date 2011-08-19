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
			setHeader("#### You are missing parameters in the config.yml, make an "
			        +"backup and delete it, when you restart the server the "
					+"pluging will auto create an updated config.yml");
			checkParameters("General.Language","EN");
			checkParameters("Sort.Sort_Key","KEY_S");
			checkParameters("Debug.Permissions","false");
			checkParameters("Debug.SortInventory","false");
			checkParameters("Debug.OnEnable","false");
			checkParameters("Debug.keyboard","false");
	        //save();
		} else {
			loadFileFromJar(CONFIG_FILE);
		}
	}


	private void checkParameters(String key, String std) {
		// TODO Auto-generated method stub
        if(this.getString(key) == null) {
           this.setProperty(key, std);
        }
	}

	private static File loadFileFromJar(String filename) {
		File actual = new File(SortInventory.PLUGIN_FOLDER, filename);
		if (!actual.exists()) {
			InputStream input = Config.class
					.getResourceAsStream("/" + filename);
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
		if (string != null) {
			if (SortInventory.config.getString(string).equalsIgnoreCase(
					"true")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
