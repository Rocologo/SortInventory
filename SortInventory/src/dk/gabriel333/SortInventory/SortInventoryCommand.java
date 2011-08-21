package dk.gabriel333.SortInventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
			if (!G333Permissions.hasPerm(sender, "use")) {
				sender.sendMessage(ChatColor.RED
						+ "You to dont have proper permissions for that command."
						+ " (" + G333Plugin.PLUGIN_NAME.toLowerCase()
						+ ".use)");
				return true;
			} else {
				if (targetblock.getType() == Material.CHEST) {
					Chest chest = (Chest) targetblock.getState();
					SortChestInventory.sortinventory(sPlayer, chest);
					G333Messages.sendNotification(sPlayer, "Items sorted.");
				} else {
					SortPlayerInventory.sortinventory(sPlayer,
							ScreenType.CHAT_SCREEN);
					G333Messages.sendNotification(sPlayer, "Items sorted.");
				}
				return true;
			}
		} else {
			G333Messages.showWarning("You can't use /sort in the console.");
			return false;
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

	static void moveitem(SpoutPlayer p, int fromslot, int toslot,
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


}
