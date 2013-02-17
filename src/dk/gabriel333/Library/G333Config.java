package dk.gabriel333.Library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.bukkit.plugin.Plugin;  
import org.bukkit.util.config.Configuration;

@SuppressWarnings("deprecation")
public class G333Config extends Configuration {
	public String LIBRARY_LANGUAGE;
	public String LIBRARY_SORTKEY;
	public String LIBRARY_MENUKEY;
	public String LIBRARY_SORTSEQ;
	public String[] SORTSEQ;

	public Boolean DEBUG_PERMISSIONS;
	public Boolean DEBUG_SORTINVENTORY;
	public Boolean DEBUG_ONENABLE;
	public Boolean DEBUG_KEYBOARD;

	public G333Config(File file) {
		super(file);
		if (!file.exists())
			loadFileFromJar(CONFIG_FILE);
		load();
		LIBRARY_LANGUAGE = getStringParm("Library.Language", "EN");
		LIBRARY_SORTKEY = getStringParm("Library.SortKey", "KEY_S");
		LIBRARY_MENUKEY = getStringParm("Library.MenuKey", "KEY_M");
		LIBRARY_SORTSEQ = getStringParm("Library.SortSEQ", "STONE,COBBLESTONE,DIRT,WOOD");
		
		SORTSEQ = LIBRARY_SORTSEQ.split(",");

		DEBUG_PERMISSIONS = getBooleanParm("Debug.Permissions", false);
		DEBUG_SORTINVENTORY = getBooleanParm("Debug.Inventory", false);
		DEBUG_ONENABLE = getBooleanParm("Debug.OnEnable", false);
		DEBUG_KEYBOARD = getBooleanParm("Debug.Keyboard", false);

		setHeader(
				"###########################################################",
				"# This is an autogenerated config.yml, because you had an #",
				"# old version of the config.yml. I recommended that you   #",
				"# backup your current config.yml and then delete it from  #",
				"# from the plugin directory and reload the server, to     #",
				"# get a fresh config.yml                                  #",
				"#                                                         #",
				"#                                                         #",
				"###########################################################");
		if (dosave) {
			G333Messages.showWarning("YOUR CONFIG.YML IS NOT UPTODATE");
			save();
		}
			
	}

	public static String CONFIG_FILE = "config.yml";
	public static G333Config g333Config;

	public static void setupConfig(Plugin plugin) {
		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdirs();
		File configfile = new File(plugin.getDataFolder(),
				G333Config.CONFIG_FILE);
		g333Config = new G333Config(configfile);
	}

	private Boolean dosave = false;

	private String getStringParm(String string, String def) {
		String str = getString(string);
		if (str == null) {
			dosave = true;
			G333Messages.showWarning("Missing parameter:" +string);
			return getString(string,def);
		} else
			return str;
	}

	private Boolean getBooleanParm(String string, Boolean def) {
		String str = getString(string);
		if (str == null) {
			dosave = true;
			G333Messages.showWarning("Missing parameter:" +string);
		} 
		return getBoolean(string,def);
	}

	private static File loadFileFromJar(String filename) {
		File actual = new File(G333Plugin.PLUGIN_FOLDER, filename);
		if (!actual.exists()) {
			InputStream input = G333Config.class.getResourceAsStream("/"
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
					G333Messages.showWarning("The file: " + filename
							+ " has been created.");
				} catch (Exception e) {
					G333Messages.showStackTrace(e);
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

}