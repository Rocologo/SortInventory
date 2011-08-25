package dk.gabriel333.SortInventory;

import java.util.UUID;

import org.bukkit.event.Event;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.player.SpoutPlayer;

//import dk.gabriel333.SortInventory.*;

public class SortInventoryMenuListener extends SpoutListener {
	public static SortInventory plugin;

	public void SortInventoryMenu(SortInventory plugin) {
		SortInventoryMenu.plugin = plugin;
	}

	public void onCustomEvent(Event event) {

		// fejler ScreenType screentype = ((ScreenEvent) event).getScreenType();
		
		if (event instanceof ButtonClickEvent) {
			// We are going to need some other way to differentiate button
			// events
			Button button = ((ButtonClickEvent) event).getButton();
			UUID uuid = button.getId();
			SpoutPlayer sPlayer = ((ButtonClickEvent) event).getPlayer();

			if (SortInventoryMenu.sortInventoryMenuButtons.get(uuid) == "Sort") {
				sPlayer.sendMessage("Sort button");
				
				
				
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
