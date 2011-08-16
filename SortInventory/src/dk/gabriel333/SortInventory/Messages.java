package dk.gabriel333.SortInventory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.getspout.spoutapi.player.SpoutPlayer;


public class Messages {
	
    public static final Logger l = Logger.getLogger("Minecraft");
      	   	
    public static void showInfo(String message) {
    	l.log(Level.INFO, "["+SortInventory.PLUGIN_NAME+"] " + message);
    }

    public static void showError(String message) {
    	l.log(Level.SEVERE, "["+SortInventory.PLUGIN_NAME+"] " + message);
    }

    public static void showWarning(String message) {
    	l.log(Level.WARNING, "["+SortInventory.PLUGIN_NAME+"] " + message);
    }

    public static void showStackTrace(Throwable t) {
    	l.log(Level.SEVERE, t.getMessage(), t);
    }
    
    public static void sendNotification(SpoutPlayer sPlayer, String string) {
    	//SpoutPlayer sPlayer = (SpoutPlayer) player;
    	if (sPlayer.isSpoutCraftEnabled()  && (sPlayer instanceof SpoutPlayer)) {
    	//if(Safety.spout && (sPlayer instanceof SpoutPlayer)) { 
			sPlayer.sendNotification(sPlayer.getName(), string, Material.DIAMOND);
		} else {
			sPlayer.sendMessage(string);
		}
    }
}
