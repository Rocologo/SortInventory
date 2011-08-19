package dk.gabriel333.SortInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public abstract class SortPlayerInventory implements Player {

	public static void sortinventory(SpoutPlayer sPlayer) {
		Inventory inventory = sPlayer.getInventory();
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {
			ItemStack item1 = inventory.getItem(i);
			if ((item1.getAmount() == 64)
					|| (i < 9 && (item1.getAmount() == 0
							|| SortInventoryCommand.isTool(item1)
							|| SortInventoryCommand.isWeapon(item1)
							|| SortInventoryCommand.isArmor(item1)
							|| SortInventoryCommand.isFood(item1) ||
					// Food must be alone in slot 0-8 so you can eat it.
					SortInventoryCommand.isVehicle(item1)))) {
				continue;
			} else {
				for (j = i + 1; j < inventory.getSize(); j++) {
					SortInventoryCommand.moveitem(sPlayer, j, i, inventory,
							inventory);
				}
			}
		}
		// sort the SpoutBackpack if it exists.
		sPlayer.sendMessage("spoutbackpack:"+SortInventory.spoutbackpack);
		if (SortInventory.spoutbackpack) {
			inventory = SortInventory.spoutBackpackHandler.getOpenedSpoutBackpack(sPlayer);
			i = 0;
			j = 0;
			for (i = 0; i < inventory.getSize(); i++) {
				ItemStack item1 = inventory.getItem(i);
				if (item1.getAmount() == 64) {
					continue;
				} else {
					for (j = i + 1; j < inventory.getSize(); j++) {
						SortInventoryCommand.moveitem(sPlayer, j, i, inventory,
								inventory);
					}
				}
			}
		}
	}
}
