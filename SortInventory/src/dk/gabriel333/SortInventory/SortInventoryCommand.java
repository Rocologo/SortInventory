package dk.gabriel333.SortInventory;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SortInventoryCommand implements CommandExecutor {
	public SortInventoryCommand(SortInventory instance) {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		SpoutPlayer sPlayer = (SpoutPlayer) sender;
		Block targetblock = sPlayer.getTargetBlock(null, 4);
		if (SortInventory.isPlayer(sender)) {
			if (!SortInventory.hasPerm(sender, "use")) {
				sender.sendMessage(ChatColor.RED
						+ "You to dont have proper permissions for that command."
						+ " (" + SortInventory.PLUGIN_NAME.toLowerCase()
						+ ".use)");
				return true;
			} else {
				if (targetblock.getType() == Material.CHEST) {
					Chest chest = (Chest) targetblock.getState();
					SortChestInventory.sortinventory(sPlayer, chest);
					Messages.sendNotification(sPlayer, "Items sorted.");
				} else {
					SortPlayerInventory.sortinventory(sPlayer);
					Messages.sendNotification(sPlayer, "Items sorted.");
				}
				return true;
			}
		} else {
			Messages.l
					.log(Level.WARNING, "You can't use /sort in the console.");
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

	// Contains the list of stackable items
	protected static final int stackableitems[] = { 1, 3, 4, 5, 6, 7, 12, 13,
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
			31, 32, 33, 35, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
			50, 52, 53, 54, 56, 57, 58, 60, 61, 63, 64, 65, 66, 67, 68, 69, 70,
			71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87,
			88, 89, 91, 92, 93, 94, 95, 96 };

	// Check if it is in the list
	public static boolean isStackable(ItemStack item) {
		for (int i : stackableitems) {
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
		if ((from_amt == 0 && to_amt==0) || from_amt==0){
			//if (SortInventory.isEnabled("Debug.SortInventory")) {
			//	p.sendMessage("0) Both slot is empty: (from,to)=("+fromslot+">"+toslot+") Dont do anything");
			//}
			return;
		} else if (to_amt==0 && from_amt>0) {
			to_amt = total_amt;
			from_amt = 0;
			if (SortInventory.isEnabled("Debug.SortInventory")) {
				p.sendMessage("1) (from,to)=("+fromslot+">"+toslot+") To_amt=" + to_amt + " from_amt="
						+ from_amt + " total_amt=" + total_amt);
			}
			toinventory.setItem(toslot, fromitem);
			toinventory.getItem(toslot).setAmount(to_amt);
			frominventory.clear(fromslot);
			return;
		} else { 
			// Here is to_amt > and from_amt>0 so move all whats posible if 
			// it is the same kind of item.
			
			if (SortInventory.isEnabled("Debug.SortInventory")) {
				p.sendMessage("2) slot("+fromslot+">"+toslot+") getData:(" + fromitem.getData() + "," + toitem.getData()
						+ ") Durability: (" + fromitem.getDurability() +"," +toitem.getDurability()
						+ ") TypeId: ("+ fromitem.getTypeId() + "," +toitem.getTypeId()+")");
			}
			
	
			if (fromitem.getTypeId() == toitem.getTypeId()
				//&& fromitem.getData().equals(toitem.getData())  
				&& fromitem.getDurability() == toitem.getDurability()) {
				
				if (fromitem.getData()!= null && toitem.getData()!=null) {
					if (!fromitem.getData().equals(toitem.getData())) {
						p.sendMessage("3) DONT MOVE! slot("+fromslot+">"+toslot+") getData:(" + fromitem.getData() + "," + toitem.getData()
								+ ") Durability: (" + fromitem.getDurability() +"," +toitem.getDurability()
								+ ") TypeId: ("+ fromitem.getTypeId() + "," +toitem.getTypeId()+")");			
						return;
					}
					
				}
				
				if (total_amt > 64) {
					to_amt = 64;
					from_amt = total_amt - 64;
					if (SortInventory.isEnabled("Debug.SortInventory")) {
						p.sendMessage("4) To_amt=" + to_amt + " from_amt="
								+ from_amt + " total_amt=" + total_amt);
					}
					fromitem.setAmount(from_amt);
					toitem.setAmount(to_amt);
					return;
				} else {
					// total_amt is <= 64 so everything goes to toslot
					if (SortInventory.isEnabled("Debug.SortInventory")) {
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
