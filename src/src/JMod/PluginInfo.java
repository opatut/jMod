package JMod;
import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;


public class PluginInfo {
	public PluginInfo(String name, File current_config) {
		mServerStatus = ServerStatus.None;
		
		try {
			mLatestConfig = PluginDownloader.getInstance().DownloadPluginConfig(name);
			mCurrentConfig = new PluginConfig();
			mCurrentConfig.LoadFromURL(current_config.toURI().toURL());
			
			if (!mCurrentConfig.GetProperty("general.name").equals(mLatestConfig.GetProperty("general.name"))) { 
				System.err.println("Fatal Error: Current and latest config plugin name do not match.");
				System.err.println("> Requested: " + name);
				System.err.println("> Current:   " + mCurrentConfig.GetProperty("general.name"));
				System.err.println("> Latest:    " + mLatestConfig.GetProperty("general.name"));
				System.exit(1);
			}
		} catch (IOException e) {
			System.out.println("Could not create PluginInfo: config file not found or download failed.");
		}
	}
	
	public File GetCurrentFile() {
		return new File(Minecraft.getMinecraftDir(), "plugins/" + GetName() + "-" + GetCurrentConfigVersion() + "/plugin.jar");
	}
	
	public File GetLatestFile() {
		return new File(Minecraft.getMinecraftDir(), "plugins/" + GetName() + "-" + GetLatestConfigVersion() + "/plugin.jar");
	}
	
	public boolean IsUpToDate() {
		return GetLatestConfigVersion() == GetCurrentConfigVersion();
	}
	
	public String GetName() {
		return mCurrentConfig.GetProperty("general.name");
	}
	
	public int GetCurrentConfigVersion() {
		return Integer.parseInt(mCurrentConfig.GetProperty("general.config_version"));
	}
	
	public int GetLatestConfigVersion() {
		return Integer.parseInt(mLatestConfig.GetProperty("general.config_version"));
	}
	
	public boolean IsInitiallyEnabled() {
		return ModOptions.getInstance().EnabledPlugins.contains(GetName());
	}
	
	public boolean IsCurrentlyEnabled() {
		if(PluginLoader.getInstance().mLoadedPlugins.containsKey(GetName()))
			return PluginLoader.getInstance().mLoadedPlugins.get(GetName()).mEnabled;
		else 
			return false;
	}
	
	public boolean IsLoaded() {
		return PluginLoader.getInstance().mLoadedPlugins.containsKey(GetName());
	}
	
	public PluginConfig mLatestConfig;
	public PluginConfig mCurrentConfig;
	
	public ServerStatus mServerStatus;
		
	public enum ServerStatus {
		None,
		Proposed,
		Required,
		Forbidden
	}
}
