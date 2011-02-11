package JMod;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

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
		System.out.println(System.getProperty("java.library.path"));
		RefreshPluginList();
		if (!LoadPlugin("TestPlugin1")) {
			System.out.println("Loading failed.");
		} else {
			EnablePlugin("TestPlugin1");
		}
		ModListener.getInstance().HandleEvent(new JMod.Event(EventType.UpdateGame));
			
	}
	
	public void RefreshPluginList() {
		mAvailablePlugins.clear();
		File folder = new File("plugins");
		File[] files = folder.listFiles();
		for (File f: files) {	
			if(f.isFile() && f.getName().toLowerCase().endsWith(".jar"))
				mAvailablePlugins.add(f);
		}
		System.out.print("Refreshed list of available plugins:");
		for(File s: mAvailablePlugins) {
			System.out.print(" " + s.getName().replaceAll("\\.jar$", ""));
		}
		System.out.println();
	}
	
	// Get the singleton instance / create one if none exists
	public static PluginLoader getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new PluginLoader();
        }
        return INSTANCE;
    }
	
	public boolean LoadPlugin(String name) {
		for(File f: mAvailablePlugins) {
			if(f.getName().startsWith(name) && f.getName().length() == name.length() + 4) { // ".jar"
				try {
					URLClassLoader loader = new URLClassLoader(
							new URL[]{f.toURI().toURL()});
					
					Plugin m = (Plugin) Class.forName(name, true, loader).newInstance();
					System.out.println("Loading Plugin: " + m.GetName() + "...");
					m.OnInitialize();
					mPlugins.put(name, m);
					System.out.println("Plugin " + m.GetName() + " loaded.");
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
	}
	
	public void DisablePlugin(String name) {
		if(mPlugins.containsKey(name) && mPlugins.get(name).mEnabled) {
			mPlugins.get(name).mEnabled = false;
			mPlugins.get(name).OnDisable();
		}
	}
	
	public HashMap<String, Plugin> mPlugins;
	public ArrayList<File> mAvailablePlugins;
	private static PluginLoader INSTANCE = null;
}
