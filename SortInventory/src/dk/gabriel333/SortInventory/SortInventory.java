package dk.gabriel333.SortInventory;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.Screen;

import dk.gabriel333.Library.G333Config;
import dk.gabriel333.Library.G333Messages;
import dk.gabriel333.Library.G333Plugin;

import me.neatmonster.spoutbackpack.SBHandler;

public class SortInventory extends JavaPlugin {
	public static SortInventory instance;

	public static Boolean spout = false;
	
	// Hook into SpoutBackpack
	public static SBHandler spoutBackpackHandler; //The Backpack inventoryHandler
	public static Boolean spoutbackpack = false; // is SpoutBackpack installed.

	// GUI
	public Screen sortScreen = new GenericPopup();
	
	@Override
	public void onEnable() {
		instance = this;

		G333Plugin.setupPlugin(this);
		G333Config.setupConfig(this);
		setupSpout();
		setupSpoutBackpack();
		setupGUI();
		registerEvents();
		addCommands();

		PluginDescriptionFile pdfFile = this.getDescription();
		G333Messages.showInfo(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is enabled!");
	}
	    
	private void setupGUI() {
		// TODO Auto-generated method stub
		//sortScreen=sPlayer.getMainScreen();
	}

	public void registerEvents() {
		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.CUSTOM_EVENT, new KeyListener(),
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.CUSTOM_EVENT, new SortGuiListener(), 
				Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type.CUSTOM_EVENT, new SortInventoryListener(), 
		//	Event.Priority.Normal, this);
		
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		G333Messages.showInfo(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is disabled!");
	}

	public void addCommands() {
		// Register commands
		getCommand("Sort").setExecutor(new SortInventoryCommand(this));
	}



	private void setupSpout() {
		Plugin spoutPlugin = this.getServer().getPluginManager()
				.getPlugin("Spout");
		if (spoutPlugin != null) {
			spout = true;
			G333Messages.showInfo("Spout is detected.");
		} else {
			G333Messages.showError("Safety is dependend on Spout!");
		}
	}

	private void setupSpoutBackpack() {
		if (spoutBackpackHandler != null) {
			return;
		}
		Plugin spoutBackpackPlugin = this.getServer().getPluginManager()
				.getPlugin("SpoutBackpack");
		if (spoutBackpackPlugin == null) {
			return;
		}
		spoutBackpackHandler = new SBHandler();
		spoutbackpack = true;
		if (spout == true) {
			G333Messages.showInfo("SpoutBackpack is detected.");
		} else {
			G333Messages.showWarning("SpoutBackpack is detected, but spout is not detected.");
			spoutbackpack = false;
		}
		return;
	}

	public static boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player)
			return true;
		return false;
	}

}
