package dk.gabriel333.SortInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericScreen;
//import org.getspout.spoutapi.gui.GenericScreen;
//import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SortGUI {
	@SuppressWarnings("unused")
	private SortInventory plugin;
	public SpoutPlayer sPlayer;
	public HashMap<UUID, String> sortInventoryButtons = new HashMap<UUID, String>();
	public GenericScreen sortScreen=(GenericScreen) sPlayer.getMainScreen();


	public SortGUI(SortInventory plugin) {
		this.plugin = plugin;
	}
	
	public SortGUI(SpoutPlayer sPlayer) {
		GenericButton button;
		ArrayList<GenericButton> buttons = new ArrayList<GenericButton>();
		button = new GenericButton("SORT");
		//button.setHexColor(hex)
		//button.setHoverColor(hexColor)
		button.setX(30).setY(30).setHeight(12).setWidth(50);
		buttons.add(button);
		button.setTooltip("this is a tooltip");
		//button.setColor(hexColor)
		button.setVisible(true);
		sortInventoryButtons.put(button.getId(), "sort");	
		//sortScreen.attachWidget(button);
		//sPlayer.getMainScreen().setDirty(true);
		
			
	}
	
	
}
