package JMod;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ini4j.*;
import org.ini4j.Profile.Section;

public class PluginConfig {
	
	public PluginConfig() {
		mProperties = new HashMap<String, String>();
	}

	public boolean LoadFromURL(URL url) throws IOException {
		mIni = new Ini();
		mIni.load(new InputStreamReader(url.openStream()));
		
		if(mIni.containsKey("ERROR")) {
			int code = Integer.parseInt(mIni.get("ERROR", "code"));
			String desc = mIni.get("ERROR", "desc");
			if(code == 404) {
				System.out.println("Plugin not found: " + desc);
			} else {
				System.out.println("Unknown error: " + desc);
			}
			return false;
		} else {
			for(Entry<String, Section> entry: mIni.entrySet()) {
				for(Entry<String,String> pair: entry.getValue().entrySet()) {
					mProperties.put(entry.getKey().toLowerCase() + "." + pair.getKey().toLowerCase(), pair.getValue());
				}
			}
			return true;
		}
	}
	
	public void SaveToFile(File file) {
		if(mIni != null)
			try {
				mIni.store(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public String GetProperty(String key) {
		return mProperties.get(key.toLowerCase());
	}

	private HashMap<String,String> mProperties;
	private Ini mIni;
}
