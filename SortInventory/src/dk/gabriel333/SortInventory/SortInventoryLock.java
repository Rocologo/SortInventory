package dk.gabriel333.SortInventory;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.getspout.spoutapi.player.SpoutPlayer;

import dk.gabriel333.Library.G333Config;
import dk.gabriel333.Library.G333Messages;
import dk.gabriel333.Library.G333Permissions;
import dk.gabriel333.Library.G333Plugin;

public class SortInventoryLock implements CommandExecutor {
	private final SortInventory plugin;

	public SortInventoryLock(SortInventory instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		SpoutPlayer sPlayer = (SpoutPlayer) sender;
		Block targetblock = sPlayer.getTargetBlock(null, 4);
		String pincode = "0000";
		String owner = sPlayer.getName();
		Integer closetimer = 0; // never closes automatically

		if (SortInventory.isPlayer(sPlayer)) {
			if (!G333Permissions.hasPerm(sPlayer, "lock",G333Permissions.NOT_QUIET)) {
				sPlayer.sendMessage(ChatColor.RED
						+ "You to dont have proper permissions for that command."
						+ " (" + G333Plugin.PLUGIN_NAME+ ".lock)");
			}
		} else {
			G333Messages.showError("You cant use this command in the console.");
			return false;
		}

		if (args.length == 0) {
			showSafetylockStatus();
			sPlayer.sendMessage("Usage: /safetylock [lock,unlock,reset,help]");
			return true;
		}

		sPlayer.sendMessage("You typed /" + command
				+ " and permissions to use it.");
		sPlayer.sendMessage("args: " + args[0].toString());
		sPlayer.sendMessage("args.length: " + args.length);

		String action = args[0];
		if (action.equalsIgnoreCase("lock")) {
			Material material = targetblock.getType();
			if (isLockable(material)) {
				sPlayer.sendMessage("You want to lock :"
						+ targetblock.getType());
				// if USEGUI then
				if (sPlayer.isSpoutCraftEnabled()) {

					// test if you pointed at a lockable block - DONE
					// open window
					// enter number one way or another
					// set status locked on block
					// close windows
					// notification

					// TODO: integrate to iConomy
					pincode="0000";
					owner=sPlayer.getName();
					closetimer=0;
					SaveSafetylock(targetblock, pincode, owner, closetimer);

				} else {
					if (args[1] != null) {
						pincode = args[1];
						sPlayer.sendMessage("the code is:" + pincode);
					}
					if (args[2] != null) {
						owner = args[2];
						sPlayer.sendMessage("Owner:" + owner);
					}
					SaveSafetylock(targetblock, pincode, owner, closetimer);
					// syntax is /safetylock lock pincode owner
					// example /safetylock lock 0000 Gabriel333
				}
			} else {
				sPlayer.sendMessage("You can't lock a " + targetblock.getType()
						+ " block.");
				return true;
			}
		} else if (action.equalsIgnoreCase("unlock")) {
			sPlayer.sendMessage("You want to unlock :" + targetblock.getType());
			unlockSafetylock();
		} else if (action.equalsIgnoreCase("reset")) {
			sPlayer.sendMessage("You want to reset lock at:"
					+ targetblock.getType());
			resetSafetylock();
			sPlayer.sendMessage("The widget was closed");
		} else {
			sPlayer.sendMessage("You did something wrong....");
			sPlayer.sendMessage("Action: " + args[0] + " No. arguments is "
					+ args.length);
		}
		return true;
	}

	private void SaveSafetylock(Block block, String pincode, String owner,
			Integer closetimer) {
		String query = null;
		// insert/update targetblock, code, owner, closetime into safetylocks
		// table
		if (!this.checkBlock(block)) {
			// TODO: Block exists - update data.
			query = "UPDATE safetylocks SET pincode='" + pincode + "', owner='"
					+ owner + "', closetimer=" + closetimer + " WHERE x = "
					+ block.getX() + " AND y = " + block.getY() + " AND z = "
					+ block.getZ() + ";";

			return;
		} else {
			query = "INSERT INTO safetylocks (pincode, owner, closetimer, x, y, z) VALUES ('"
					+ pincode
					+ "', '"
					+ owner
					+ "', "
					+ closetimer
					+ ", "
					+ block.getX()
					+ ", "
					+ block.getY()
					+ ", "
					+ block.getZ()
					+ ");";
		}
		if (G333Config.g333Config.DEBUG_SQL) 
			G333Messages.showInfo("SQL: "+query);
		if (G333Config.g333Config.STORAGE_TYPE.equals("MySQL")) {
			try {
				this.plugin.manageMySQL.insertQuery(query);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// sPlayer.sendMessage(ChatColor.GREEN +
			// "This block is now owned by alta189");
		} else {
			this.plugin.manageSQLite.insertQuery(query);
		}
	}

	public Boolean checkBlock(Block block) {
		String query = "SELECT * FROM safetylocks WHERE x = " + block.getX()
				+ " AND y = " + block.getY() + " AND z = " + block.getZ() + ";";
		// TODO: add and rownum<2 to select only one row
		ResultSet result = null;

		if (G333Config.g333Config.STORAGE_TYPE.equals("MySQL")) {
			try {
				result = this.plugin.manageMySQL.sqlQuery(query);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else { // SQLLITE
			result = this.plugin.manageSQLite.sqlQuery(query);
		}

		try {
			if (result != null && result.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private void resetSafetylock() {
		// TODO Auto-generated method stub
	}

	private void unlockSafetylock() {
		// TODO Auto-generated method stub
	}

	private void showSafetylockStatus() {
		// TODO Auto-generated method stub

	}

	public static final Material lockablematerials[] = { Material.CHEST,
			Material.IRON_DOOR, Material.WOOD_DOOR, Material.FURNACE,
			Material.IRON_DOOR_BLOCK, Material.LOCKED_CHEST, Material.LEVER,
			Material.SIGN, Material.SIGN_POST, Material.STONE_BUTTON,
			Material.STORAGE_MINECART, Material.WALL_SIGN, Material.WOODEN_DOOR };

	// check if material is a lockable block
	private boolean isLockable(Material m) {
		if (m == null)
			return false;
		for (Material i : lockablematerials) {
			if (i == m)
				return true;
		}
		return false;
	}

}
