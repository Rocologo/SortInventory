package dk.gabriel333.SortInventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

import dk.gabriel333.Library.*; 


public class KeyListener extends InputListener {

	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		String keypressed = event.getKey().name();
		if (keypressed == null) {
			return;
		}
		SpoutPlayer sPlayer = event.getPlayer();
		ScreenType screentype = event.getScreenType();
		String hotkey = G333Config.g333Config.LIBRARY_SORTKEY;

		// sPlayer.sendMessage("EventName:" + event.getEventName() + " Type:"
		// + event.getType() + " ScreenType:" + event.getScreenType());

		if (screentype == ScreenType.CHEST_INVENTORY
				|| screentype == ScreenType.PLAYER_INVENTORY) {
			if (hotkey.equals(keypressed)) {
				Block targetblock = sPlayer.getTargetBlock(null, 4);
				if (SortInventory.isPlayer(sPlayer)) {
					if (!G333Permissions.hasPerm(sPlayer, "use")) {
						sPlayer.sendMessage(ChatColor.RED
								+ "You to dont have proper permissions for that command."
								+ " ("
								+ G333Plugin.PLUGIN_NAME.toLowerCase()
								+ ".use)");
					} else {
						if (targetblock.getType() == Material.CHEST) {
							Chest chest = (Chest) targetblock.getState();
							G333Inventory.sortChestInventory(sPlayer, chest);
							G333Messages.sendNotification(sPlayer,
									"Chest sorted.");
						} else {
							SortPlayerInventory.sortinventory(sPlayer,
									event.getScreenType());
							G333Messages.sendNotification(sPlayer,
									"Items sorted.");
						}
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
