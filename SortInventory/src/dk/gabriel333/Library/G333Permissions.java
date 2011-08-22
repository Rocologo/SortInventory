package dk.gabriel333.Library;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

//import PermissionsEx classes
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class G333Permissions {

	public static String PERMISSION_NODE;

	// Hook into Permissions 3.xxx
	public static PermissionHandler permission3Handler;
	public static Boolean permissions3 = false; // Permissions3 or
												// SuperpermBridge
	// Hook into PermissionsBukkit
	public static Boolean permissionsBukkit = false; // PermissionsBukkit
														// support

	// Hook into PermissionsEx
	public static Boolean permissionsex = false; // Permissionsex support

	// Initialize all permissionsplugins
	public static void setupPermissions(Plugin plugin) {
		PERMISSION_NODE = plugin.getDescription().getName() + ".";
		if (permissions3 || permissionsBukkit || permissionsex) {
			G333Messages
					.showWarning("Permissions3 or PermissionsBukkit is allready detected!");
			return;
		} else {

			Plugin permissions3Plugin = plugin.getServer().getPluginManager()
					.getPlugin("Permissions");
			Plugin permissionsBukkitPlugin = plugin.getServer()
					.getPluginManager().getPlugin("PermissionsBukkit");
			Plugin permissionsexPlugin = plugin.getServer().getPluginManager()
					.getPlugin("PermissionsEx");
			if (permissions3Plugin == null && permissionsBukkitPlugin == null
					&& permissionsexPlugin == null) {
				G333Messages
						.showInfo("PermissionsBukkit/Permissions3/PermissionsEx system not detected, defaulting to OP");
				return;
			} else if (permissionsBukkitPlugin != null) {
				permissionsBukkit = true;
				G333Messages.showInfo("PermissionsBukkit is detected.");
			} else if (permissions3Plugin != null) {
				permission3Handler = ((Permissions) permissions3Plugin)
						.getHandler();
				permissions3 = true;
				G333Messages
						.showInfo("Permissions3/SuperpermBridge is detected. "
								+ ((Permissions) permissions3Plugin)
										.getDescription().getFullName());
				if (permissionsBukkit) {
					G333Messages
							.showInfo("OBS. If conflict in PermissionsBukkit and Permissions3/SuperPerm, PermissionsBukkit wins over Permissions/SuperPerm");
				}

			} else if (permissionsexPlugin != null) {
				G333Messages.showInfo("PermissionsEx is detected.");
				permissionsex = true;
			}

		}
	}

	// Test if the player has permissions to do the action
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

		// Permission check
		// if(permissions.has(player, "yourplugin.permission")){
		// yay!
		// } else {
		// houston, we have a problems :)
		// }

		// if (sender.isOp())
		// return true;

		Player p = (Player) sender;
		if (sender.isOp() && (sender instanceof Player)) {
			// OP is allowed
			return true;
		} else if (permissionsBukkit) {
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
		} else if (permissionsex) {
			PermissionManager permissionsexManager = PermissionsEx
					.getPermissionManager();
			if (permissionsexManager.has(p, PERMISSION_NODE.toLowerCase() + label.toLowerCase())) {
				//G333Messages.showInfo("Permission node:"+PERMISSION_NODE+label+" TRUE");
				return true;
			} else {
				//G333Messages.showInfo("Permission node:"+PERMISSION_NODE+label+" FALSE");
				return false;
			}
		} else {
			// The user had no permissions. returns false;
			return false;
		}
	}
}
