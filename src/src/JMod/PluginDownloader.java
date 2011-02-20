package JMod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import JMod.DownloadException.Reason;

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
	

	public boolean DownloadPlugin(PluginConfig conf) throws DownloadException {
		try {
			URL url = new URL(conf.GetProperty("download.source"));
			String name = conf.GetProperty("general.name");
			File file = new File(Minecraft.getMinecraftDir(), 
					"plugins/" + name + ".jar");
			file.getParentFile().mkdirs();
			PluginDownloaderThread t = new PluginDownloaderThread(name, url, file);
			t.start();
			mThreads.put(name,t);
			return true;
		} catch (MalformedURLException e) {
			throw new DownloadException(Reason.SERVER_NOT_FOUND, conf.GetProperty("general.name"), "Malformed URL");
		}
	}
	
	public PluginConfig DownloadPluginConfig(String name) throws DownloadException {
		URL url;
		try {
			url = new URL("http://opatut.dyndns.org:81/jmod/index.php/plugins/get/" + name);
		} catch (MalformedURLException e) {
			throw new DownloadException(Reason.SERVER_NOT_FOUND, name, "Malformed URL");
		}
		
		PluginConfig conf = new PluginConfig();
		conf.LoadFromURL(url);
		
        File file = new File(Minecraft.getMinecraftDir(), "plugins/latest/" + name + ".config");
        file.getParentFile().mkdirs();
        conf.SaveToFile(file);
        
		return conf;
	}
	
	public void InstallPlugins(HashMap<String, PluginInfo> plugins_to_install)  {
		for(Entry<String, PluginInfo> e: plugins_to_install.entrySet()) {
			try {
				DownloadPlugin(e.getKey());
			} catch (DownloadException e1) {
				e.getValue().mDownloadFailed = true;
				e.getValue().mDownloadFailException = e1;
			}
		}
	}
	
	public boolean DownloadPlugin(String name) throws DownloadException {
		return DownloadPlugin(DownloadPluginConfig(name));
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

	public static final String SERVER_URL = "http://opatut.dyndns.org:81/jmod/";
	public static final String DOWNLOAD_URL = SERVER_URL + "plugins/get/%s";
	public static final String SEARCH_URL = SERVER_URL + "plugins/search/%s";
}
