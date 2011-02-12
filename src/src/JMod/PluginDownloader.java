package JMod;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

public class PluginDownloader {
	// Make constructor private so this class can only be used
	// via singleton
	private PluginDownloader() {
		mThreads = new HashMap<String, PluginDownloaderThread>();
		mConfigs = new HashMap<String, String>();
	}
	
	// Get the singleton instance / create one if none exists
	public static PluginDownloader getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new PluginDownloader();
        }
        
        return INSTANCE;
    }
	
	public String DownloadConfigFile(String name) throws IOException {
		String config = "";
		URL url = new URL("http://opatut.dyndns.org:81/jmod/config/" + name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		while((config += reader.readLine()) != null)
			config += System.getProperty("line.separator");
        reader.close();
        mConfigs.put(name,config);
		return config;
	}
	
	public boolean DownloadPlugin(String name) {
		try {
			URL url = new URL("http://opatut.dyndns.org:81/jmod/get/" + name);
			File file = new File("plugins/" + name + ".jar");
			
			PluginDownloaderThread t = new PluginDownloaderThread(url, file);
			t.start();
			mThreads.put(name,t);
			
			return true;
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
	private HashMap<String,String> mConfigs;
	private HashMap<String,PluginDownloaderThread> mThreads;
	private static PluginDownloader INSTANCE = null;
}
