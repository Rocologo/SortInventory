package dk.gabriel333.SortInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

//import de.Keyle.MyWolf.MyWolfPlugin;
import dk.gabriel333.Library.G333Inventory;
//import dk.gabriel333.Library.G333Messages;

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
					G333Inventory.moveitemInventory(sPlayer, j, i, inventory);
				}
			}
		}
		G333Inventory.orderInventoryItems(inventory, 9);

		// sort the SpoutBackpack if it exists and if it is opened.
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
							G333Inventory.moveitemInventory(sPlayer, j, i,
									inventory);
						}
					}
				}
				G333Inventory.orderInventoryItems(inventory, 0);
			}
		}

		// sort the players MyWolfInventory if exists and if is open.
		if (SortInventory.mywolf) {
			// && screentype == ScreenType.CHEST_INVENTORY) {

			// if the wolf inventory is open then  
			// myWolfInventory = MyWolfPlugin.getMyWolf(sPlayer).inv;
			// if (myWolfInventory != null) {

				// test if myWolfInventory is opened

				// G333Messages.showInfo("WolfInventory is size"
				//		+ myWolfInventory.getSize() + " name:"
				//		+ myWolfInventory.getName());

				// sorting myWolfInventry
				//G333Messages.showInfo("Sorting MyWolfInventory... to be done");
				//for (i = 0; i < myWolfInventory.getSize(); i++) {
				//	for (j = i + 1; j < myWolfInventory.getSize(); j++) {
				//		G333Inventory.moveitemInventory(sPlayer, j, i,
				//				(Inventory) myWolfInventory);
                //
				//	}
				//}
				//G333Inventory.orderInventoryItems(inventory, 0);

			//}

		}
	}
}
