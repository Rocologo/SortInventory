package dk.gabriel333.SortInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

import dk.gabriel333.Library.G333Inventory;

public abstract class SortPlayerInventory implements Player {

	public static void sortinventory(SpoutPlayer sPlayer, ScreenType screentype) {
		Inventory inventory = sPlayer.getInventory();
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {
			ItemStack item1 = inventory.getItem(i);
			if ((item1.getAmount() == 64)
					|| (i < 9 && (item1.getAmount() == 0
							|| G333Inventory.isTool(item1)
							|| G333Inventory.isWeapon(item1)
							|| G333Inventory.isArmor(item1)
							|| G333Inventory.isFood(item1) ||
					// Food must be alone in slot 0-8 so you can eat it.
					G333Inventory.isVehicle(item1)))) {
				continue;
			} else {
				for (j = i + 1; j < inventory.getSize(); j++) {
					G333Inventory.moveitem(sPlayer, j, i, inventory, inventory);
				}
			}
		}

		G333Inventory.orderItems(inventory, 9);

		// sort the SpoutBackpack if it exists.
		if (SortInventory.spoutbackpack
				&& screentype == ScreenType.CHEST_INVENTORY) {
			inventory = SortInventory.spoutBackpackHandler
					.getOpenedSpoutBackpack(sPlayer);
			// i = 0;
			// j = 0;
			if (inventory != null) {
				for (i = 0; i < inventory.getSize(); i++) {
					if (inventory.getItem(i).getAmount() == 64) {
						continue;
					} else {
						for (j = i + 1; j < inventory.getSize(); j++) {
							G333Inventory.moveitem(sPlayer, j, i, inventory,
									inventory);
						}
					}
				}
				G333Inventory.orderItems(inventory, 0);
			}
		}
	}
}
