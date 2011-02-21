package JMod;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class ModOptions {
	// SINGLETON
	private ModOptions() {
		EnabledPlugins = new ArrayList<String>();
	}
	public static void createInstance() {
		if(INSTANCE == null)
			INSTANCE = new ModOptions();
	}
	public static ModOptions getInstance() {
		return INSTANCE;
	}
	
	public boolean Load() {
		ConfigFile conf = new ConfigFile();
		if(!conf.LoadFromFile(GetFile())) {
			System.err.println("Could not load jMod options.");
			return false;
		} 
		// ============================================================
		String enabled = conf.GetProperty("plugins.enabled", "");
		String[] split = enabled.split(",");
		EnabledPlugins.clear();
		for(String s: split)
			if(s.trim().length() != 0)
				EnabledPlugins.add(s.trim());
		// ============================================================	
		return true;
	}
	
	public File GetFile() {
		return new File(Minecraft.getMinecraftDir(), ConfigFileName);
	}
	
	public boolean Save() {
		try {
			ConfigFile conf = new ConfigFile();
			// ============================================================
			String enabled = "";
			for(String s: EnabledPlugins)
				enabled += s + ",";
			conf.SetProperty("plugins.enabled", enabled);
			// ============================================================
			conf.SaveToFile(GetFile());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public ArrayList<String> EnabledPlugins;
	private static ModOptions INSTANCE = null;
	public final static String ConfigFileName = "jMod.properties"; 
}
