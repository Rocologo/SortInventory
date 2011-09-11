package dk.gabriel333.SortInventory;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.getspout.spoutapi.block.SpoutChest;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.player.SpoutPlayer;

import dk.gabriel333.Library.G333Inventory;
import dk.gabriel333.Library.G333Messages;

//import dk.gabriel333.SortInventory.*;

public class SortInventoryMenuListener extends SpoutListener {
	public static SortInventory plugin;

	public void SortInventoryMenu(SortInventory plugin) {
		SortInventoryMenu.plugin = plugin;
	}

	public void onCustomEvent(Event event) {

		//ScreenType screentype = event.getScreenType();
		
		if (event instanceof ButtonClickEvent) {
			// We are going to need some other way to differentiate button
			// events
			Button button = ((ButtonClickEvent) event).getButton();
			UUID uuid = button.getId();
			SpoutPlayer sPlayer = ((ButtonClickEvent) event).getPlayer();

			if (SortInventoryMenu.sortInventoryMenuButtons.get(uuid) == "Sort") {
				sPlayer.sendMessage("Sort button");
				Block targetblock = sPlayer.getTargetBlock(null, 4);
				if (targetblock.getType() == Material.CHEST) {
					SpoutChest sChest = (SpoutChest) targetblock
							.getState();
					G333Inventory.sortInventoryItems(sPlayer,
							sChest.getLargestInventory());
					G333Messages.sendNotification(sPlayer,
							"Chest sorted.");
				} else {
					G333Inventory.stackPlayerInventoryItems(sPlayer);
					G333Messages.sendNotification(sPlayer,
							"Items sorted.");
				}
				
				
				
			}
			if (SortInventoryMenu.sortInventoryMenuButtons.get(uuid) == "Lock") {
				sPlayer.sendMessage("Lock button");
			}
			
			if (SortInventoryMenu.sortInventoryMenuButtons.get(uuid) == "Close") {
				sPlayer.sendMessage("Close button");
				SortInventoryMenu.popup.close();
			}
		}
	}
}
