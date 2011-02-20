package JMod;
import java.io.File;

import net.minecraft.client.Minecraft;


public class PluginInfo {
	public PluginInfo(String name) {
		mName = name;
		mServerStatus = ServerStatus.None;
		mCurrentConfig = null;
		mLatestConfig = null;
		mDownloadFailed = false;
	}
	
	public void LoadCurrentConfig(File current_config) {
		mCurrentConfig = new PluginConfig();
		if(!mCurrentConfig.LoadFromFile(current_config, false)) {
			// disable plugin
			System.err.println("Could not load current plugin info. Disabling plugin: " + mName);
			PluginLoader.getInstance().DisablePlugin(mName);
			mInstalled = false;
		} else {
			mInstalled = true;
		}
	}
	
	public void LoadLatestConfig() throws DownloadException {
		mLatestConfig = PluginDownloader.getInstance().DownloadPluginConfig(mName);	
	}
		
	public File GetCurrentFile() {
		return new File(Minecraft.getMinecraftDir(), "plugins/" + GetName() + ".jar");
	}
	
	public boolean IsUpToDate() throws DownloadException {
		if(!mInstalled) 
			return false;
		if(mLatestConfig == null)
			LoadLatestConfig();
		return GetLatestConfigVersion() == GetCurrentConfigVersion();
	}
	
	public String GetName() {
		return mName;
	}
	
	public int GetCurrentConfigVersion() {
		if(!mInstalled) return 0;
		return Integer.parseInt(mCurrentConfig.GetProperty("general.config_version"));
	}
	
	public int GetLatestConfigVersion() throws DownloadException {
		if(mLatestConfig == null)
			LoadLatestConfig();
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
	public boolean mInstalled;
	
	private String mName;
	
	public boolean mDownloadFailed;
	public DownloadException mDownloadFailException;
		
	public enum ServerStatus {
		None,
		Proposed,
		Required,
		Forbidden
	}
}
