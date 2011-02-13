package JMod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import org.ini4j.Ini;

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
		try {
			Ini ini = new Ini();
			ini.load(GetFile());
			// ============================================================
			String enabled = ini.get("plugins", "enabled");
			String[] split = enabled.split(",");
			EnabledPlugins.clear();
			for(String s: split)
				EnabledPlugins.add(s.trim());
			// ============================================================
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public File GetFile() {
		return new File(Minecraft.getMinecraftDir(), "jmod.ini");
	}
	
	public boolean Save() {
		try {
			Ini ini = new Ini();
			// ============================================================
			String enabled = "";
			for(String s: EnabledPlugins)
				enabled += s + ",";
			ini.put("plugins", "enabled", enabled);
			// ============================================================
			ini.store(GetFile());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public ArrayList<String> EnabledPlugins;
	private static ModOptions INSTANCE = null;
}
