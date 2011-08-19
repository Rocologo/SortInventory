package dk.gabriel333.SortInventory;

//import me.neatmonster.spoutbackpack.SpoutBackpack;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;
//import org.getspout.spout.inventory.CustomInventory;

public class KeyListener extends InputListener {

	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		String keypressed = event.getKey().name();
		if (keypressed == null) {
			return;
		}
		SpoutPlayer sPlayer = event.getPlayer();
		ScreenType screentype = event.getScreenType();
		String hotkey = SortInventory.config.getString("Sort.SortKey", "KEY_S");

		// sPlayer.sendMessage("EventName:" + event.getEventName() + " Type:"
		// + event.getType() + " ScreenType:" + event.getScreenType());

		// sPlayer.sendMessage("Config Hotkey is:"+hotkey);
		if (screentype == ScreenType.CHEST_INVENTORY
				|| screentype == ScreenType.PLAYER_INVENTORY) {
			if (hotkey.equals(keypressed)) {
				Block targetblock = sPlayer.getTargetBlock(null, 4);
				if (SortInventory.isPlayer(sPlayer)) {
					if (!SortInventory.hasPerm(sPlayer, "use")) {
						sPlayer.sendMessage(ChatColor.RED
								+ "You to dont have proper permissions for that command."
								+ " ("
								+ SortInventory.PLUGIN_NAME.toLowerCase()
								+ ".use)");
					} else {
						if (targetblock.getType() == Material.CHEST) {
							Chest chest = (Chest) targetblock.getState();
							SortChestInventory.sortinventory(sPlayer, chest);
							Messages.sendNotification(sPlayer, "Items sorted.");
						} else {
							SortPlayerInventory.sortinventory(sPlayer);
							Messages.sendNotification(sPlayer, "Items sorted.");
						}
						//SpoutBackpack.
						// CustomInventory inv = new CustomInventory(
						// SpoutBackpack.inventoriesSize.get(sPlayer.getName()),
						// SpoutBackpack.inventoryName);
						// SpoutBackpack.openedInventories.put(sPlayer.getName(),
						// inv);
						// if
						// (SpoutBackpack.inventories.containsKey(sPlayer.getName()))
						// {
						// inv.setContents(SpoutBackpack.inventories.get(sPlayer.getName()));
                        // }
					}
				}
			}
		}
	}
	/*
	 * @Override public void onKeyReleasedEvent(KeyReleasedEvent event) {
	 * ShortcutPlayer player = ShortcutPlayer.get(event.getPlayer());
	 * player.keyUp(event.getKey());
	 * //event.getPlayer().sendMessage(event.getKey()+" up!"); }
	 */

}
