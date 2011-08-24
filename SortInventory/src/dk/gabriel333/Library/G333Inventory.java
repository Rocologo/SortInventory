package dk.gabriel333.Library;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spout.inventory.CustomInventory;
import org.getspout.spoutapi.player.SpoutPlayer;

public class G333Inventory {

	public static void sortChestInventory(SpoutPlayer sPlayer, Chest chest) {
		Block block = chest.getBlock();
		Block nextblock = null;
		// USE this http://getspout.org/jd/org/getspout/spoutapi/block/SpoutChest.html
		CustomInventory doublechestInventory = new CustomInventory(54,
				"SortInventory");
		if (block.getRelative(BlockFace.EAST).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.EAST);
			Chest chest2 = (Chest) nextblock.getState();
			doublechestInventory = getDoublechestInventory(chest2, chest);
			stackCustomInventoryItems(sPlayer, doublechestInventory);
			orderCustomInventoryItems(doublechestInventory);
			splitDoublechestInventory(doublechestInventory,
					chest, chest2);
			// G333Messages.showInfo("END of EAST");
		} else if (block.getRelative(BlockFace.WEST).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.WEST);
			Chest chest2 = (Chest) nextblock.getState();
			doublechestInventory = getDoublechestInventory(chest, chest2);
			stackCustomInventoryItems(sPlayer, doublechestInventory);
			orderCustomInventoryItems(doublechestInventory);
			splitDoublechestInventory(doublechestInventory,
					chest, chest2);
			// G333Messages.showInfo("END of WEST");
		} else if (block.getRelative(BlockFace.NORTH).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.NORTH);
			Chest chest2 = (Chest) nextblock.getState();
			doublechestInventory = getDoublechestInventory(chest2, chest);
			stackCustomInventoryItems(sPlayer, doublechestInventory);
			orderCustomInventoryItems(doublechestInventory);
			splitDoublechestInventory(doublechestInventory,
					chest, chest2);
			// G333Messages.showInfo("END of NORTH");
		} else if (block.getRelative(BlockFace.SOUTH).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.SOUTH);
			Chest chest2 = (Chest) nextblock.getState();
			doublechestInventory = getDoublechestInventory(chest, chest2);
			stackCustomInventoryItems(sPlayer, doublechestInventory);
			orderCustomInventoryItems(doublechestInventory);
			splitDoublechestInventory(doublechestInventory,
					chest, chest2);
			// G333Messages.showInfo("END of SOUTH");
		} else {
			stackInventoryItems(sPlayer, chest.getInventory());
			orderInventoryItems(chest.getInventory(), 0);
		}
	}

	public static CustomInventory getDoublechestInventory(Chest chest1,
			Chest chest2) {
		CustomInventory custominventory = new CustomInventory(54,
				"SortInventory");
		for (int i = 0; i < 27; i++) {
			custominventory.setItem(i, chest1.getInventory().getItem(i));
			custominventory.setItem((i + 27), chest2.getInventory().getItem(i));
		}
		return custominventory;
	}

	private static void stackCustomInventoryItems(SpoutPlayer sPlayer,
			CustomInventory custominventory) {
		int i, j;
		for (i = 0; i < custominventory.getSize(); i++) {
			for (j = i + 1; j < custominventory.getSize(); j++) {
				moveitemCustomInventory(sPlayer, j, i, custominventory);
			}
		}
	}

	public static void orderCustomInventoryItems(
			CustomInventory custominventory) {
		int n = 0;
		for (int m = 0; m < G333Config.g333Config.SORTSEQ.length; m++) {
			Material mat = Material
					.matchMaterial(G333Config.g333Config.SORTSEQ[m]);
			if (mat == null) {
				G333Messages.showError("Configuration error i config.yml.");
				G333Messages.showError(" Unknown material in SORTSEQ:"
						+ G333Config.g333Config.SORTSEQ[m]);
			} else if (custominventory.contains(mat)) {
				for (int i = n; i < custominventory.getSize(); i++) {
					if (custominventory.getItem(i).getType() == mat) {
						n++;
						continue;
					} else {
						for (int j = i + 1; j < custominventory.getSize(); j++) {
							if (custominventory.getItem(j).getType() == mat) {
								switchCustomInventoryItems(custominventory, i,
										j);
								n++;
								break;
							}
						}
					}
				}

			}
		}
	}

	private static void switchCustomInventoryItems(
			CustomInventory custominventory, int slot1, int slot2) {
		ItemStack item = custominventory.getItem(slot1);
		custominventory.setItem(slot1, custominventory.getItem(slot2));
		custominventory.setItem(slot2, item);
	}

	public static void moveitemCustomInventory(SpoutPlayer p, int fromslot,
			int toslot, CustomInventory custominventory) {

		int from_amt, to_amt, total_amt;
		ItemStack fromitem, toitem;
		fromitem = custominventory.getItem(fromslot);
		toitem = custominventory.getItem(toslot);
		from_amt = fromitem.getAmount();
		to_amt = toitem.getAmount();
		total_amt = from_amt + to_amt;
		if ((from_amt == 0 && to_amt == 0) || from_amt == 0) {
			// Dont do anything
			return;
		} else if (to_amt == 0 && from_amt > 0) {
			to_amt = total_amt;
			from_amt = 0;
			if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
				G333Messages.showInfo("1) (from,to)=(" + fromslot + ">"
						+ toslot + ") To_amt=" + to_amt + " from_amt="
						+ from_amt + " total_amt=" + total_amt);
			}
			custominventory.setItem(toslot, fromitem);
			custominventory.getItem(toslot).setAmount(to_amt);
			custominventory.clear(fromslot);
			return;
		} else {
			// Here is to_amt > and from_amt>0 so move all what's possible if
			// it is the same kind of item.
			if (G333Permissions.hasPerm(p, "stack.*")) {
				// okay...
			} else if ((isTool(fromitem) && !G333Permissions.hasPerm(p,
					"stack.tools"))
					|| (isWeapon(fromitem) && !G333Permissions.hasPerm(p,
							"stack.weapons"))
					|| (isArmor(fromitem) && !G333Permissions.hasPerm(p,
							"stack.armor"))
					|| (isFood(fromitem) && !G333Permissions.hasPerm(p,
							"stack.food"))
					|| (isVehicle(fromitem) && !G333Permissions.hasPerm(p,
							"stack.vehicles"))) {
				return;
			}

			if (fromitem.getTypeId() == toitem.getTypeId()
					&& fromitem.getDurability() == toitem.getDurability()) {

				if (fromitem.getData() != null && toitem.getData() != null) {
					if (!fromitem.getData().equals(toitem.getData())) {
						// DONT MOVE ANYTHING
						return;
					}
				}
				if (total_amt > 64) {
					to_amt = 64;
					from_amt = total_amt - 64;
					if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
						G333Messages.showInfo("4) To_amt=" + to_amt
								+ " from_amt=" + from_amt + " total_amt="
								+ total_amt);
					}
					fromitem.setAmount(from_amt);
					toitem.setAmount(to_amt);
					return;
				} else {
					// total_amt is <= 64 so everything goes to toslot
					if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
						G333Messages.showInfo("5) To_amt=" + to_amt
								+ " from_amt=" + from_amt + " total_amt="
								+ total_amt);
					}
					custominventory.setItem(toslot, fromitem);
					custominventory.getItem(toslot).setAmount(total_amt);
					custominventory.clear(fromslot);
					return;
				}
			}
		}
	}

	public static void splitDoublechestInventory(
			CustomInventory custominventory, Chest chest1, Chest chest2) {
		for (int i = 0; i < 27; i++) {
			if (custominventory.getItem(i).getAmount() != 0) {
				chest1.getInventory().setItem(i, custominventory.getItem(i));
			} else {
				chest1.getInventory().clear(i);
			}
			if (custominventory.getItem(i + 27).getAmount() != 0) {
				chest2.getInventory().setItem(i, custominventory.getItem(i + 27));
			} else {
				chest2.getInventory().clear(i);
			}
		}

	}

	// ********************************************************************
	// ********************************************************************
	// ***************** INVENTORY METHODS*********************************
	// ********************************************************************
	// ********************************************************************

	private static void stackInventoryItems(SpoutPlayer sPlayer,
			Inventory inventory) {
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {
			for (j = i + 1; j < inventory.getSize(); j++) {
				moveitemInventory(sPlayer, j, i, inventory);
			}
		}
	}

	private static void switchInventoryItems(Inventory inventory, int slot1,
			int slot2) {
		ItemStack item = inventory.getItem(slot1);
		inventory.setItem(slot1, inventory.getItem(slot2));
		inventory.setItem(slot2, item);
	}

	public static void moveitemInventory(SpoutPlayer p, int fromslot,
			int toslot, Inventory inventory) {
		int from_amt, to_amt, total_amt;
		ItemStack fromitem, toitem;
		fromitem = inventory.getItem(fromslot);
		toitem = inventory.getItem(toslot);
		from_amt = fromitem.getAmount();
		to_amt = toitem.getAmount();
		total_amt = from_amt + to_amt;
		if ((from_amt == 0 && to_amt == 0) || from_amt == 0) {
			// Dont do anything
			return;
		} else if (to_amt == 0 && from_amt > 0) {
			to_amt = total_amt;
			from_amt = 0;
			if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
				G333Messages.showInfo("1) (from,to)=(" + fromslot + ">"
						+ toslot + ") To_amt=" + to_amt + " from_amt="
						+ from_amt + " total_amt=" + total_amt);
			}
			inventory.setItem(toslot, fromitem);
			inventory.getItem(toslot).setAmount(to_amt);
			inventory.clear(fromslot);
			return;
		} else {
			// Here is to_amt > and from_amt>0 so move all what's possible if
			// it is the same kind of item.
			if (G333Permissions.hasPerm(p, "stack.*")) {
				// okay...
			} else if ((isTool(fromitem) && !G333Permissions.hasPerm(p,
					"stack.tools"))
					|| (isWeapon(fromitem) && !G333Permissions.hasPerm(p,
							"stack.weapons"))
					|| (isArmor(fromitem) && !G333Permissions.hasPerm(p,
							"stack.armor"))
					|| (isFood(fromitem) && !G333Permissions.hasPerm(p,
							"stack.food"))
					|| (isVehicle(fromitem) && !G333Permissions.hasPerm(p,
							"stack.vehicles"))) {
				return;
			}
			if (fromitem.getTypeId() == toitem.getTypeId()
					&& fromitem.getDurability() == toitem.getDurability()) {
				if (fromitem.getData() != null && toitem.getData() != null) {
					if (!fromitem.getData().equals(toitem.getData())) {
						// DONT MOVE ANYTHING
						return;
					}
				}
				if (total_amt > 64) {
					to_amt = 64;
					from_amt = total_amt - 64;
					if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
						G333Messages.showInfo("4) To_amt=" + to_amt
								+ " from_amt=" + from_amt + " total_amt="
								+ total_amt);
					}
					fromitem.setAmount(from_amt);
					toitem.setAmount(to_amt);
					return;
				} else {
					// total_amt is <= 64 so everything goes to toslot
					if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
						G333Messages.showInfo("5) To_amt=" + to_amt
								+ " from_amt=" + from_amt + " total_amt="
								+ total_amt);
					}
					inventory.setItem(toslot, fromitem);
					inventory.getItem(toslot).setAmount(total_amt);
					inventory.clear(fromslot);
					return;
				}
			}
		}
	}

	public static void orderInventoryItems(Inventory inventory, int startslot) {
		int n = startslot;
		for (int m = 0; m < G333Config.g333Config.SORTSEQ.length; m++) {
			Material mat = Material
					.matchMaterial(G333Config.g333Config.SORTSEQ[m]);
			if (mat == null) {
				G333Messages.showError("Configuration error i config.yml.");
				G333Messages.showError(" Unknown material in SORTSEQ:"
						+ G333Config.g333Config.SORTSEQ[m]);
			} else if (inventory.contains(mat)) {
				for (int i = n; i < inventory.getSize(); i++) {
					if (inventory.getItem(i).getType() == mat) {
						n++;
						continue;
					} else {
						for (int j = i + 1; j < inventory.getSize(); j++) {
							if (inventory.getItem(j).getType() == mat) {
								switchInventoryItems(inventory, i, j);
								n++;
								break;
							}
						}
					}
				}

			}
		}
	}

	// ********************************************************************
	// ********************************************************************
	// *****************INVENTORY TOOLS************************************
	// ********************************************************************
	// ********************************************************************

	// Contains the list of tools
	protected static final int tools[] = { 256, 257, 258, 269, 270, 271, 273,
			274, 275, 277, 278, 279, 284, 285, 286, 290, 291, 292, 293, 294,
			346 };

	// Check if it is a tool
	public static boolean isTool(ItemStack item) {
		for (int i : tools) {
			if (item.getTypeId() == i) {
				return true;
			}
		}
		return false;
	}

	// Contains the list of weapons
	protected static final int weapons[] = { 267, 268, 272, 276, 283, 261 };

	// Check if it is a weapon
	public static boolean isWeapon(ItemStack item) {
		for (int i : weapons) {
			if (item.getTypeId() == i) {
				return true;
			}
		}
		return false;
	}

	// Contains the list of armors
	protected static final int armors[] = { 298, 299, 300, 301, 302, 303, 304,
			305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317 };

	// Check if it is a armor
	public static boolean isArmor(ItemStack item) {
		for (int i : armors) {
			if (item.getTypeId() == i) {
				return true;
			}
		}
		return false;
	}

	// Contains the list of foods
	protected static final int foods[] = { 260, 282, 297, 319, 320, 322, 349,
			350, 354 };

	// Check if it is food
	public static boolean isFood(ItemStack item) {
		for (int i : foods) {
			if (item.getTypeId() == i) {
				return true;
			}
		}
		return false;
	}

	// Contains the list of vehicles
	protected static final int vehicles[] = { 328, 333, 342, 343 };

	// Check if it is a vehicles
	public static boolean isVehicle(ItemStack item) {
		for (int i : vehicles) {
			if (item.getTypeId() == i) {
				return true;
			}
		}
		return false;
	}

	public Boolean isDoublechest(Chest chest) {
		if (chest.getBlock().getRelative(BlockFace.EAST).getType() == Material.CHEST) {
			return true;
		} else if (chest.getBlock().getRelative(BlockFace.WEST).getType() == Material.CHEST) {
			return true;

		} else if (chest.getBlock().getRelative(BlockFace.NORTH).getType() == Material.CHEST) {
			return true;

		} else if (chest.getBlock().getRelative(BlockFace.SOUTH).getType() == Material.CHEST) {
			return true;
		} else {
			return false;
		}
	}

	// ********************************************************************
	// ********************************************************************
	// *****************UNUSED*********************************************
	// ********************************************************************
	// ********************************************************************

}
