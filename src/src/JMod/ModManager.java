package JMod;

public class ModManager {
	public static void InitializeMod() {
		ModOptions.createInstance();
		ModOptions.getInstance().Load();
		
		ModListener.createInstance();
		PluginLoader.createInstance();
		PluginLoader.getInstance().LoadEnabledPlugins();
	}
	
	public static void AfterInitialization() {
	}
}
