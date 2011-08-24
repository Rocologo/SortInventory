package dk.gabriel333.SortInventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.getspout.spoutapi.event.inventory.InventoryEvent;

@SuppressWarnings("serial")
public class SortInventoryOpenEvent extends InventoryEvent {

	public SortInventoryOpenEvent(String event, Player player,
			Inventory inventory) {
		super(event, player, inventory);
		// TODO Auto-generated constructor stub
	    // player.sendMessage("The event is:"+event);
	}
	
}