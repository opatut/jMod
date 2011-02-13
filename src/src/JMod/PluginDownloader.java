package JMod;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import net.minecraft.client.Minecraft;

public class PluginDownloader {
	// Make constructor private so this class can only be used
	// via singleton
	private PluginDownloader() {
		mThreads = new HashMap<String, PluginDownloaderThread>();
	}
	
	// Get the singleton instance / create one if none exists
	public static PluginDownloader getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new PluginDownloader();
        }
        
        return INSTANCE;
    }
	

	public boolean DownloadPlugin(PluginConfig conf) {
		try {
			URL url = new URL(conf.GetProperty("download.source"));
			String name = conf.GetProperty("general.name");
			File file = new File(Minecraft.getMinecraftDir(), 
					"plugins/" + name + "-" + conf.GetProperty("general.config_version") + "/plugin.jar");
			file.getParentFile().mkdirs();
			PluginDownloaderThread t = new PluginDownloaderThread(name, url, file);
			t.start();
			mThreads.put(name,t);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public PluginConfig DownloadPluginConfig(String name) throws IOException {
		URL url = new URL("http://opatut.dyndns.org:81/jmod/index.php/plugins/get/" + name);
		
		PluginConfig conf = new PluginConfig();
		conf.LoadFromURL(url);
		
        File file = new File(Minecraft.getMinecraftDir(), "plugins/" + name + "-" + conf.GetProperty("general.config_version") + "/plugin.config");
        file.getParentFile().mkdirs();
        conf.SaveToFile(file);
        
		return conf;
	}
	
	public boolean DownloadPlugin(String name) {
		try {
			return DownloadPlugin(DownloadPluginConfig(name));
		} catch (IOException e) {
			return false;
		}
	}
	
	public void CancelPluginDownload(String name) {
		if(mThreads.containsKey(name) && mThreads.get(name).isAlive()) {
			mThreads.get(name).Cancel();
			mThreads.remove(name);
		}
	}
	
	public void CancelAllPluginDownloads() {
		for(String s: mThreads.keySet()) {
			CancelPluginDownload(s);
		}
	}
	private HashMap<String,PluginDownloaderThread> mThreads;
	private static PluginDownloader INSTANCE = null;
}
