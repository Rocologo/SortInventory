package dk.gabriel333.SortInventory;

import java.io.File;

//import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//import permissions3 classes
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import me.neatmonster.spoutbackpack.SBHandler;

public class SortInventory extends JavaPlugin {
	public static SortInventory instance;

	public static String PLUGIN_NAME = "SortInventory";
	public static String PERMISSION_NODE = "sortinventory.";
	public static String PLUGIN_FOLDER = "./plugins/" + PLUGIN_NAME + "/";
	public static Config config;

	// Hook into Permisions
	public static PermissionHandler permission3Handler;

	public static Boolean spout = false;
	public static Boolean permissions3 = false; // Permissions3 or
												// SuperpermBridge
	public static Boolean permissionsBukkit = false; // PermissionsBukkit
	public static Boolean spoutbackpack = false; // is SpoutBackpack installed.
	public static SBHandler spoutBackpackHandler;

	@Override
	public void onEnable() {
		instance = this;
		loadConfigFile();
		setupPermissions();
		setupSpout();
		setupSpoutBackpack();
		registerEvents();
		addCommands();

		PluginDescriptionFile pdfFile = this.getDescription();
		Messages.showInfo(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is enabled!");
	}

	public void registerEvents() {
		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.CUSTOM_EVENT, new KeyListener(),
				Event.Priority.Normal, this);
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		Messages.showInfo(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is disabled!");
	}

	public void addCommands() {
		// Register commands
		getCommand("Sort").setExecutor(new SortInventoryCommand(this));
	}

	private void loadConfigFile() {
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdirs();
		File bccconfigfile = new File(PLUGIN_FOLDER, Config.CONFIG_FILE);
		config = new Config(bccconfigfile);
	}

	private void setupPermissions() {
		if (permissions3 || permissionsBukkit) {
			Messages.showWarning("Permissions3 or PermissionsBukkit is allready detected!");
			return;
		} else {
			Plugin permissions3Plugin = this.getServer().getPluginManager()
					.getPlugin("Permissions");
			Plugin permissionsBukkitPlugin = this.getServer()
					.getPluginManager().getPlugin("PermissionsBukkit");
			if (permissions3Plugin == null && permissionsBukkitPlugin == null) {
				Messages.showInfo("Permissions3/PermissionsBukkit system not detected, defaulting to OP");
				return;
			} else {
				if (permissionsBukkitPlugin != null) {
					permissionsBukkit = true;
					Messages.showInfo("PermissionsBukkit is detected.");
				}
				if (permissions3Plugin != null) {
					permission3Handler = ((Permissions) permissions3Plugin)
							.getHandler();
					permissions3 = true;
					Messages.showInfo("Permissions3/SuperpermBridge is detected. "
							+ ((Permissions) permissions3Plugin)
									.getDescription().getFullName());
					if (permissionsBukkit) {
						Messages.showInfo("OBS. If conflict in PermissionsBukkit and Permissions3/SuperPerm, PermissionsBukkit wins over Permissions/SuperPerm");

					}

				}
			}
		}
	}

	private void setupSpout() {
		Plugin spoutPlugin = this.getServer().getPluginManager()
				.getPlugin("Spout");
		if (spoutPlugin != null) {
			spout = true;
			Messages.showInfo("Spout is detected.");
		} else {
			Messages.showError("Safety is dependend on Spout!");
		}
	}

	/*
	 * private void setupSpoutBackpack() { if (spoutBackpackHandler != null) {
	 * return; } Plugin spoutPlugin = this.getServer().getPluginManager()
	 * .getPlugin("SpoutBackpack"); if (spoutPlugin != null) {
	 * spoutbackpack=true; spoutBackpackHandler = new SBHandler(); } if
	 * (spout==true) { Messages.showInfo("SpoutBackpack is detected."); } else {
	 * Messages
	 * .showWarning("SpoutBackpack is detected, but spout is not detected.");
	 * spoutbackpack=false; } }
	 */

	private void setupSpoutBackpack() {
		if (spoutBackpackHandler != null) {
			return;
		}
		Plugin spoutBackpackPlugin = this.getServer().getPluginManager()
				.getPlugin("SpoutBackpack");
		if (spoutBackpackPlugin == null) {
			return;
		}
		spoutBackpackHandler = new SBHandler();
		spoutbackpack = true;
		if (spout == true) {
			Messages.showInfo("SpoutBackpack is detected.");
		} else {
			Messages.showWarning("SpoutBackpack is detected, but spout is not detected.");
			spoutbackpack = false;
		}
		return;
	}

	public static boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player)
			return true;
		return false;
	}

	// public static boolean debugmode(String string) {
	// if (config.getString(string).toLowerCase() == "true") {
	// return true;
	// } else {
	// return false;
	// }
	// }

	// public static boolean isEnabled(String string) {
	// if (string == null) {
	// Messages.showWarning("Config.yml is not uptodate.");
	// return false;
	// } else if (config.getString(string).toLowerCase() == "true") {
	// return true;
	// } else {
	// return false;
	// }
	// }

	public static boolean hasPerm(CommandSender sender, String label) {

		// How to hook into PermissionsBukkit
		// Basic Permission Check
		// In this example (MyPlugin) is meant to represent the name of your
		// plugin,
		// for example... iConomy would look like:
		// Player player = (Player) sender;
		// if (player.hasPermission("a.custom.node") {
		// return true;
		// }

		// How to hook into Permissions
		// Basic Permission Check
		// In this example (MyPlugin) is meant to represent the name of your
		// plugin,
		// for example... iConomy would look like:
		// if (!(MyPlugin).permissionHandler.has(player, "a.custom.node")) {
		// return;
		// }
		// Checking if a user belongs to a group
		// if (!(MyPlugin).permissionHandler.inGroup(world, name, groupName)) {
		// return;
		// }

		if (sender.isOp())
			return true;

		if (permissions3 || permissionsBukkit) {
			if ((!sender.isOp()) && (sender instanceof Player)) {
				// player is not OP
				Player p = (Player) sender;
				if (permissionsBukkit) {
					if (p.hasPermission(PERMISSION_NODE + label)) {
						// if (debugmode("Debug.Permissions"))
						// p.sendMessage(ChatColor.GREEN
						// + "PermissionsBukkit: You have permission to:"
						// + PERMISSION_NODE + label);
					} else {
						// if (debugmode("Debug.Permissions"))
						// p.sendMessage(ChatColor.BLUE
						// + "PermissionsBukkit: You dont have permission to: "
						// +PERMISSION_NODE + label);
					}
					return (p.hasPermission(PERMISSION_NODE + label));
				} else if (permissions3) {
					// Permissions3 or SuperpermBridge
					if (permission3Handler.has(p, PERMISSION_NODE + label)) {
						// if (debugmode("Debug.Permissions"))
						// p.sendMessage(ChatColor.GREEN
						// + "Permissions3 You have permission to: "
						// + PERMISSION_NODE + label);
					} else {
						// if (debugmode("Debug.Permissions"))
						// p.sendMessage(ChatColor.BLUE
						// + "Permission3: You dont have permission to: "
						// + PERMISSION_NODE + label);
					}
					return (permission3Handler.has(p, PERMISSION_NODE + label));
				}
			} else {
				// player is OP
				return true;
			}
		}
		// The user had no permissions. returns false;
		return false;
	}

}
