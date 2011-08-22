package dk.gabriel333.Library;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class G333Inventory {

	public static void orderItemsDoublechest(Inventory inventory1,
			Inventory inventory2) {
		int n = 0;
		for (int m = 0; m < G333Config.g333Config.SORTSEQ.length; m++) {
			Material mat = Material
					.matchMaterial(G333Config.g333Config.SORTSEQ[m]);
			if (mat == null) {
				G333Messages.showError("Configuration error i config.yml.");
				G333Messages.showError(" Unknown material in SORTSEQ:"
						+ G333Config.g333Config.SORTSEQ[m]);
			} else if (inventory1.contains(mat)) {
				for (int i = n; i < inventory1.getSize(); i++) {
					if (inventory1.getItem(i).getType() == mat) {
						n++;
						continue;
					} else {
						for (int j = i + 1; j < inventory1.getSize(); j++) {
							if (inventory1.getItem(j).getType() == mat) {
								switchitems(inventory1, i, j);
								n++;
								break;
							}
						}
					}
				}

			}
		}
		// orderItems(inventory1, 0);
		// orderItems(inventory2, 0);
	}

	public static void orderItems(Inventory inventory, int startslot) {
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
								switchitems(inventory, i, j);
								n++;
								break;
							}
						}
					}
				}

			}
		}
	}

	public static void sortinventory(SpoutPlayer sPlayer, Chest chest) {
		Block block = chest.getBlock();
		Block nextblock = null;
		if (block.getRelative(BlockFace.EAST).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.EAST);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest2.getInventory(),
					chest.getInventory());
			orderItemsDoublechest(chest2.getInventory(), chest.getInventory());
		} else if (block.getRelative(BlockFace.WEST).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.WEST);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest.getInventory(),
					chest2.getInventory());
			orderItemsDoublechest(chest.getInventory(), chest2.getInventory());

		} else if (block.getRelative(BlockFace.NORTH).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.NORTH);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest2.getInventory(),
					chest.getInventory());
			orderItemsDoublechest(chest2.getInventory(), chest.getInventory());

		} else if (block.getRelative(BlockFace.SOUTH).getType() == Material.CHEST) {
			nextblock = chest.getBlock().getRelative(BlockFace.SOUTH);
			Chest chest2 = (Chest) nextblock.getState();
			stackItemsDoublechest(sPlayer, chest.getInventory(),
					chest2.getInventory());
			orderItemsDoublechest(chest.getInventory(), chest2.getInventory());
		} else {
			stackItemsSinglechest(sPlayer, chest.getInventory());
			orderItems(chest.getInventory(), 0);
		}
	}

	private static void stackItemsDoublechest(SpoutPlayer sPlayer,
			Inventory inventory1, Inventory inventory2) {
		int i, j;
		for (i = 0; i < inventory1.getSize(); i++) {
			for (j = i + 1; j < inventory1.getSize(); j++) {
				moveitem(sPlayer, j, i, inventory1, inventory1);
			}
			for (j = 0; j < inventory2.getSize(); j++) {
				moveitem(sPlayer, j, i, inventory2, inventory1);
			}
		}
		for (i = 0; i < inventory2.getSize(); i++) {
			for (j = i + 1; j < inventory2.getSize(); j++) {
				moveitem(sPlayer, j, i, inventory2, inventory2);
			}
		}

	}
	
	@SuppressWarnings("unused")
	private static void switchitemsDoublechest(Inventory inventory1, int slot1,
			Inventory inventory2, int slot2) {
		ItemStack item1 = inventory1.getItem(slot1);
		ItemStack item2 = inventory2.getItem(slot2);
		inventory1.setItem(slot2, item2);
		inventory2.setItem(slot1, item1);
	}

	private static void stackItemsSinglechest(SpoutPlayer sPlayer,
			Inventory inventory) {
		int i, j;
		for (i = 0; i < inventory.getSize(); i++) {
			for (j = i + 1; j < inventory.getSize(); j++) {
				moveitem(sPlayer, j, i, inventory, inventory);
			}
		}

	}

	private static void switchitems(Inventory inventory, int slot1, int slot2) {
		ItemStack item = inventory.getItem(slot1);
		inventory.setItem(slot1, inventory.getItem(slot2));
		inventory.setItem(slot2, item);
	}



	public static void moveitem(SpoutPlayer p, int fromslot, int toslot,
			Inventory frominventory, Inventory toinventory) {

		int from_amt, to_amt, total_amt;
		ItemStack fromitem, toitem;
		fromitem = frominventory.getItem(fromslot);
		toitem = toinventory.getItem(toslot);
		from_amt = fromitem.getAmount();
		to_amt = toitem.getAmount();
		total_amt = from_amt + to_amt;
		if ((from_amt == 0 && to_amt == 0) || from_amt == 0) {
			if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
				p.sendMessage("0) Both slot is empty: (from,to)=(" + fromslot
						+ ">" + toslot + ") Dont do anything");
			}
			return;
		} else if (to_amt == 0 && from_amt > 0) {
			to_amt = total_amt;
			from_amt = 0;
			if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
				p.sendMessage("1) (from,to)=(" + fromslot + ">" + toslot
						+ ") To_amt=" + to_amt + " from_amt=" + from_amt
						+ " total_amt=" + total_amt);
			}
			toinventory.setItem(toslot, fromitem);
			toinventory.getItem(toslot).setAmount(to_amt);
			frominventory.clear(fromslot);
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
			if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
				p.sendMessage("2) slot(" + fromslot + ">" + toslot
						+ ") getData:(" + fromitem.getData() + ","
						+ toitem.getData() + ") Durability: ("
						+ fromitem.getDurability() + ","
						+ toitem.getDurability() + ") TypeId: ("
						+ fromitem.getTypeId() + "," + toitem.getTypeId() + ")");
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
						p.sendMessage("4) To_amt=" + to_amt + " from_amt="
								+ from_amt + " total_amt=" + total_amt);
					}
					fromitem.setAmount(from_amt);
					toitem.setAmount(to_amt);
					return;
				} else {
					// total_amt is <= 64 so everything goes to toslot
					if (G333Config.g333Config.DEBUG_SORTINVENTORY) {
						p.sendMessage("5) To_amt=" + to_amt + " from_amt="
								+ from_amt + " total_amt=" + total_amt);
					}
					toinventory.setItem(toslot, fromitem);
					toinventory.getItem(toslot).setAmount(total_amt);
					frominventory.clear(fromslot);
					return;
				}
			}
		}
	}

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

}
