package dk.gabriel333.SortInventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

public class KeyListener extends InputListener {

	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		SpoutPlayer sPlayer = event.getPlayer();
		String keypressed = event.getKey().name();
		//if isEnabled("Debug.Sort") sPlayer.sendNotification("Key pressed:", keypressed, Material.BOOK);
		String hotkey = SortInventory.config.getString("Sort.SortKey");
		// sPlayer.sendMessage("Config Hotkey is:"+hotkey);
		if (keypressed==null) {
			//Messages.showError("Keypressed is null! Contact the plugindeveloper.");
			return;
		}
		if (hotkey==null) {
			hotkey="KEY_I";
			Messages.showError("The Sortkey is not defined in config.yml");
			return;
		}
		if (hotkey.equals(keypressed)) {
			//sPlayer.sendNotification("Key pressed:", "This is the SortKey",
			//		Material.BOOK);
			Block targetblock = sPlayer.getTargetBlock(null, 4);
			if (SortInventory.isPlayer(sPlayer)) {
				if (!SortInventory.hasPerm(sPlayer, "use")) {
					sPlayer.sendMessage(ChatColor.RED
							+ "You to dont have proper permissions for that command."
							+ " (" + SortInventory.PLUGIN_NAME.toLowerCase()
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
