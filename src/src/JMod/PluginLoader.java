package JMod;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.Minecraft;


/**
 * This class loads and manages all mods. 
 * @author opatut
 */
public class PluginLoader {
	// Make constructor private so this class can only be used
	// via singleton
	private PluginLoader() {
		mPlugins = new HashMap<String, Plugin>();
		mAvailablePlugins = new ArrayList<File>();

		RefreshPluginList();
	}
	
	public void OnServerConnect(String[] required_plugins, String[] proposed_plugins) {
		
	}
	
	public void RefreshPluginList() {
		mAvailablePlugins.clear();
		File folder = new File(Minecraft.getMinecraftDir(), "plugins");
		if(!folder.exists()) folder.mkdirs();
		
		File[] files = folder.listFiles();
		for (File f: files) {	
			if(f.isFile() && f.getName().toLowerCase().endsWith(".jar"))
				mAvailablePlugins.add(f);
		}
		/*System.out.print("Refreshed list of available plugins:");
		for(File s: mAvailablePlugins) {
			System.out.print(" " + s.getName().replaceAll("\\.jar$", ""));
		}
		System.out.println();*/
	}
	
	public void LoadEnabledPlugins() {
		String[] en = new String[0];
		en = ModOptions.getInstance().EnabledPlugins.toArray(en);
		for(String s: en) {			
			if(!IsPluginAvailable(s)) {
				// if not available, download (and load/enable after download)
				System.out.println("+ PluginLoader :: Plugin " + s + " not available. Downloading...");
				PluginDownloader.getInstance().DownloadPlugin(s);
			} else {
				LoadAndEnablePlugin(s);
			}
		}
	}
	
	public boolean IsPluginAvailable(String name) {
		for(File f: mAvailablePlugins) {
			if(f.getName().replaceAll("\\.jar$", "").equals(name))
				return true;
		}
		return false;
	}
	
	public static void createInstance() {
		if(INSTANCE == null)
			INSTANCE = new PluginLoader();
	}
	public static PluginLoader getInstance() {
		return INSTANCE;
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
		for(File f: mAvailablePlugins) {
			if(f.getName().startsWith(name) && f.getName().length() == name.length() + 4) { // ".jar"
				try {
					URLClassLoader loader = new URLClassLoader(
							new URL[]{f.toURI().toURL()});
					
					Plugin m = (Plugin) Class.forName(name, true, loader).newInstance();
					m.OnInitialize();
					mPlugins.put(name, m);
					System.out.println("+ PluginLoader :: Plugin " + m.GetName() + " loaded.");
					return true;
				} catch (Exception e) {
					System.out.println("Could not create instance of class type " + name + ".");
				}
			}
		}
		return false;
	}
	
	public void EnablePlugin(String name) {
		if(mPlugins.containsKey(name) && !mPlugins.get(name).mEnabled) {
			mPlugins.get(name).mEnabled = true;
			mPlugins.get(name).OnEnable();
		}
		if(!ModOptions.getInstance().EnabledPlugins.contains(name))
			ModOptions.getInstance().EnabledPlugins.add(name);
	}
	
	public void DisablePlugin(String name) {
		if(mPlugins.containsKey(name) && mPlugins.get(name).mEnabled) {
			mPlugins.get(name).mEnabled = false;
			mPlugins.get(name).OnDisable();
		}
		if(ModOptions.getInstance().EnabledPlugins.contains(name))
			ModOptions.getInstance().EnabledPlugins.remove(name);
	}
	
	public void PluginDownloadFinished(String name) {
		LoadAndEnablePlugin(name);
	}
	
	public HashMap<String, Plugin> mPlugins;
	public ArrayList<File> mAvailablePlugins;
	private static PluginLoader INSTANCE = null;
}
