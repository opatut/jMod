package JMod;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map.Entry;

import JMod.DownloadException.Reason;

import net.minecraft.client.Minecraft;


/**
 * This class loads and manages all mods. 
 * @author opatut
 */
public class PluginLoader {
	// Make constructor private so this class can only be used
	// via singleton
	private PluginLoader() {
		mInstalledPlugins = new HashMap<String, PluginInfo>();
		mLoadedPlugins = new HashMap<String, Plugin>();
		RefreshInstalledPluginList();
	}
	
	public static void createInstance() {
		if(INSTANCE == null)
			INSTANCE = new PluginLoader();
	}
	
	public static PluginLoader getInstance() {
		return INSTANCE;
	}
	
	public void OnServerConnect(String[] required_plugins, String[] proposed_plugins) {
		// TODO: do something
	}
	
	public void RefreshInstalledPluginList() {
		mInstalledPlugins.clear();
		File folder = new File(Minecraft.getMinecraftDir(), "plugins");
		
		if(!folder.exists()) folder.mkdirs();
		
		File[] files = folder.listFiles();
		for (File f: files) {	
			if(f.getName().toLowerCase().endsWith(".config")) {
				String name = f.getName().replaceAll("\\.config$", "");
				File jar_file = new File(folder, name + ".jar");
				if(jar_file.exists()) {
					PluginInfo inf = new PluginInfo(name);
					inf.LoadCurrentConfig(f);
					mInstalledPlugins.put(name, inf);
				}
			}
		}
	}
	
	public HashMap<String, PluginInfo> SearchForPlugins(PluginSearchQuery query) throws DownloadException {
		HashMap<String, PluginInfo> list = new HashMap<String, PluginInfo>();
		ConfigFile conf = new ConfigFile();
		try {
			if (!conf.LoadFromURL(new URL(String.format(PluginDownloader.SEARCH_URL, query.mSearchString))))
				
			if(conf.GetProperty("ERROR.code") != null) {
				int code = Integer.parseInt(conf.GetProperty("ERROR.code"));
				if(code == 204) {
					// don't do anything. there is just no result.
				} else {
					throw new DownloadException(Reason.CONFIG_NOT_FOUND, "?", "Unknown error: " + conf.GetProperty("ERROR.desc"));
				}
			} else {
				for(Entry<String, String> e: conf.GetEntries().entrySet()) {
					list.put(e.getKey(), new PluginInfo(e.getKey()));
				}
			}
		} catch (MalformedURLException e) {
			System.err.println("Could not search for plugin: " + e.getMessage());
			throw new DownloadException(Reason.SERVER_NOT_FOUND, query.mSearchString, e.getMessage());
		}
		return list;
	}
	
	public void LoadEnabledPlugins() {
		String[] en = new String[0];
		en = ModOptions.getInstance().EnabledPlugins.toArray(en);
		for(String s: en) {			
			if(!IsPluginInstalled(s)) {
				// if not installed, download (and load/enable after download)
				try {
					System.out.println("+ PluginLoader :: Plugin " + s + " not available. Downloading ...");
					PluginDownloader.getInstance().DownloadPlugin(s);
				} catch (DownloadException e) {
					System.err.println("+ PluginLoader :: Plugin download failed: " + e.Message);
				}
			} else {
				LoadAndEnablePlugin(s);
			}
		}
	}
	
	public boolean IsPluginInstalled(String name) {
		return mInstalledPlugins.containsKey(name);
	}

	public boolean LoadAndEnablePlugin(String name) {
		if (LoadPlugin(name)) {
			EnablePlugin(name);
			return true;
		} else {
			DisablePlugin(name);
		}
		return false;
	}
	
	public boolean LoadPlugin(String name) {
		if(mInstalledPlugins.containsKey(name)) {
			try {
				PluginInfo info = mInstalledPlugins.get(name);
				
				URLClassLoader loader = new URLClassLoader(new URL[]{info.GetCurrentFile().toURI().toURL()});
				
				Plugin m = (Plugin) Class.forName(name, true, loader).newInstance();
				m.OnInitialize();
				mLoadedPlugins.put(name, m);
				System.out.println("+ PluginLoader :: Plugin " + m.GetName() + " loaded.");
				return true;
			} catch (Exception e) {
				System.out.println("Could not create instance of class type " + name + ".");
			}
		}
		return false;
	}
	
	public void EnablePlugin(String name) {
		if(IsPluginInstalled(name)) {
			PluginInfo info = mInstalledPlugins.get(name); 
			if(!info.IsLoaded()) {
				LoadPlugin(name);
			}
			
			if(!info.IsCurrentlyEnabled()) {
				mLoadedPlugins.get(name).mEnabled = true;
				mLoadedPlugins.get(name).OnEnable();
			}
		}
		if(!ModOptions.getInstance().EnabledPlugins.contains(name))
			ModOptions.getInstance().EnabledPlugins.add(name);
	}
	
	public void DisablePlugin(String name) {
		if(IsPluginInstalled(name)) {
			PluginInfo info = mInstalledPlugins.get(name); 
			if(info.IsLoaded()) {
				mLoadedPlugins.get(name).mEnabled = false;
				if(info.IsCurrentlyEnabled())
					mLoadedPlugins.get(name).OnDisable();
			}
		}
		if(ModOptions.getInstance().EnabledPlugins.contains(name))
			ModOptions.getInstance().EnabledPlugins.remove(name);
	}
	
	public void DeletePlugin(String name) {
		File file = new File(Minecraft.getMinecraftDir(), "plugins");
		File[] files = file.listFiles();
		for(File f: files) {
			if(f.getName().split("-")[0].equals(name)) {
				f.delete();
			}
		}
	}
	
	public void PluginDownloadFinished(String name) {
		LoadAndEnablePlugin(name);
	}
	
	public HashMap<String, PluginInfo> mInstalledPlugins;
	public HashMap<String, Plugin> mLoadedPlugins;
	private static PluginLoader INSTANCE = null;
}
