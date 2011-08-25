package dk.gabriel333.SortInventory;

import org.getspout.spoutapi.event.inventory.InventoryCloseEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.getspout.spoutapi.event.inventory.InventoryOpenEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import dk.gabriel333.Library.G333Config;
import dk.gabriel333.Library.G333Messages;

public class SortInventoryListener extends InventoryListener {

	public SortInventory plugin;

	public SortInventoryListener(SortInventory plugin) {
		this.plugin = plugin;
	}

	public void onInventoryOpen(InventoryOpenEvent event) {
		SpoutPlayer sPlayer = (SpoutPlayer) event.getPlayer();
		G333Messages.sendNotification(sPlayer,
				G333Config.g333Config.LIBRARY_SORTKEY + ":Sort "
						+ G333Config.g333Config.LIBRARY_SORTKEY + ":Menu");
	}

	public void onInventoryClose(InventoryCloseEvent event) {
		// G333Messages.showInfo("onInventoryClose");

	}

}
