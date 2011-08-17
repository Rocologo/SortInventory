package dk.gabriel333.SortInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public abstract class SortPlayerInventory implements Player {

	public static void sortinventory(SpoutPlayer sPlayer) {
		Inventory inventory = sPlayer.getInventory();
		// int i, j, newamount;
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {
			ItemStack item1 = inventory.getItem(i);
			if ((item1.getAmount() == 64) ||
				
				(i < 9 && (
						   item1.getAmount()==0 ||		 
						   SortInventoryCommand.isTool(item1) ||
						   SortInventoryCommand.isWeapon(item1) ||
						   SortInventoryCommand.isArmor(item1) ||
						   SortInventoryCommand.isFood(item1) ||
						   // Food must be alone in slot 0-8 so you can eat it.
						   SortInventoryCommand.isVehicle(item1)
							          ))) {
				continue;
			} else {
			for (j = i + 1; j < inventory.getSize(); j++) {
				//ItemStack item2 = inventory.getItem(j);
				//if (i>8 && !(						 
				//		   SortInventoryCommand.isTool(item2) ||
				//		   SortInventoryCommand.isWeapon(item2) ||
				//		   SortInventoryCommand.isArmor(item2) ||
				//		   SortInventoryCommand.isFood(item2) ||
				//		   SortInventoryCommand.isVehicle(item2)
				//			          ))
				 //sPlayer.sendMessage("0) Move from j="+j+" to i="+i);
				SortInventoryCommand.moveitem(sPlayer, j, i, inventory, inventory);}
			
			}
		}
	}

}
