package dk.gabriel333.SortInventory;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.block.CraftChest;
import org.bukkit.inventory.Inventory;
import org.getspout.spoutapi.player.SpoutPlayer;


import dk.gabriel333.Library.G333Inventory;

public class SortChestInventory extends CraftChest {

	public SortChestInventory(Block block) {
		super(block);
	}

	public static void sortinventory(SpoutPlayer sPlayer, Chest chest) {
		Block block = chest.getBlock();
		Block nextblock = null;
		if (block.getRelative(BlockFace.EAST).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.EAST);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest.getInventory(),
					chest2.getInventory());
		} else if (block.getRelative(BlockFace.WEST).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.WEST);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest.getInventory(),
					chest2.getInventory());

		} else if (block.getRelative(BlockFace.NORTH).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.NORTH);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest.getInventory(),
					chest2.getInventory());

		} else if (block.getRelative(BlockFace.SOUTH).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.SOUTH);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest.getInventory(),
					chest2.getInventory());
		} else {
			stackItemsSinglechest(sPlayer, chest.getInventory());
		}
	}

	private static void stackItemsDoublechest(SpoutPlayer sPlayer,
			Inventory inventory1, Inventory inventory2) {
		int i, j;
		for (i = 0; i < inventory1.getSize(); i++) {
			for (j = i + 1; j < inventory1.getSize(); j++) {
				SortInventoryCommand.moveitem(sPlayer, j, i, inventory1,
						inventory1);
			}
			for (j = 0; j < inventory2.getSize(); j++) {
				SortInventoryCommand.moveitem(sPlayer, j, i, inventory2,
						inventory1);
			}
		}
		for (i = 0; i < inventory2.getSize(); i++) {
			for (j = i + 1; j < inventory2.getSize(); j++) {
				SortInventoryCommand.moveitem(sPlayer, j, i, inventory2,
						inventory2);
			}
		}
		G333Inventory.orderItemsDoublechest(inventory1, inventory2);
	}

	private static void stackItemsSinglechest(SpoutPlayer sPlayer,
			Inventory inventory) {
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {

			for (j = i + 1; j < inventory.getSize(); j++) {

				SortInventoryCommand.moveitem(sPlayer, j, i, inventory,
						inventory);
			}
		}
		G333Inventory.orderItems(inventory, 1);
	}

	
	

}
