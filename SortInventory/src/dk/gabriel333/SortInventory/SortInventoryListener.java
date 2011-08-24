package dk.gabriel333.SortInventory;

import org.getspout.spoutapi.event.inventory.InventoryCloseEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.getspout.spoutapi.event.inventory.InventoryOpenEvent;

//import dk.gabriel333.Library.G333Messages;

public class SortInventoryListener extends InventoryListener {

	public void onInventoryOpen(InventoryOpenEvent event) {
		// G333Messages.showInfo("onInventoryOpen");
		// G333Messages.showInfo("eventname:"+event.getEventName()
		//		+" invname"+event.getInventory().getName()+
		//		" event.type:"+
		//		event.getType());

		// if (ConfigBuffer.WolfChestOpened.contains(event.getPlayer())
		// && ConfigBuffer.mWolves
		// .containsKey(event.getPlayer().getName())) {
		// ConfigBuffer.mWolves.get(event.getPlayer().getName()).Wolf
		// .setSitting(false);
		// ConfigBuffer.WolfChestOpened.remove(event.getPlayer());
		// }
	}

	public void onInventoryClose(InventoryCloseEvent event) {
		// G333Messages.showInfo("onInventoryClose");

		// if (ConfigBuffer.WolfChestOpened.contains(event.getPlayer())
		// && ConfigBuffer.mWolves
		// .containsKey(event.getPlayer().getName())) {
		// ConfigBuffer.mWolves.get(event.getPlayer().getName()).Wolf
		// .setSitting(false);
		// ConfigBuffer.WolfChestOpened.remove(event.getPlayer());
		// }
	}

}
