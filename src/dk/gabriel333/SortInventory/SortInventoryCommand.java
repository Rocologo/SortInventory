package dk.gabriel333.SortInventory;

import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.getspout.spoutapi.block.SpoutChest;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

import dk.gabriel333.Library.*;

public class SortInventoryCommand implements CommandExecutor {
	public SortInventoryCommand(SortInventory instance) {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		SpoutPlayer sPlayer = (SpoutPlayer) sender;
		Block targetblock = sPlayer.getTargetBlock(null, 4);
		if (SortInventory.isPlayer(sender)) {
			if (G333Permissions.hasPerm(sender, "use",
					G333Permissions.NOT_QUIET)) {
				// G333Messages.showInfo("Target is:"+targetblock.getType());
				if (targetblock.getType() == Material.CHEST) {
					SpoutChest sChest = (SpoutChest) targetblock.getState();
					G333Inventory.sortInventoryItems(sPlayer,
							sChest.getLargestInventory());
					G333Messages.sendNotification(sPlayer, "Chest sorted.");
				} else {
					SortPlayerInventory.sortinventory(sPlayer,
							ScreenType.CHAT_SCREEN);
					G333Messages.sendNotification(sPlayer, "Items sorted.");
				}
			}
			return true;
		} else {
			G333Messages.showWarning("You can't use /sort in the console.");
			return false;
		}
	}

}
