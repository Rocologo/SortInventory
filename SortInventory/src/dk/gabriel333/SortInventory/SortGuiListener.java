package dk.gabriel333.SortInventory;

import java.util.UUID;

import org.bukkit.event.Event;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SortGuiListener extends SpoutListener {
	public void onCustomEvent(Event event) {
		

		if (event instanceof ButtonClickEvent) {
			// We are going to need some other way to differentiate button
			// events
			Button button = ((ButtonClickEvent) event).getButton();
			UUID uuid = button.getId();
			SpoutPlayer sPlayer = ((ButtonClickEvent) event).getPlayer();
			
			sPlayer.sendMessage("UUID:"+uuid);
		}
	}
}
