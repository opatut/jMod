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
		
		Ini ini = new Ini();
		ini.load(new InputStreamReader(url.openStream()));
		for(Entry<String, Section> entry: ini.entrySet()) {
			for(Entry<String,String> pair: entry.getValue().entrySet()) {
				mProperties.put(entry.getKey().toLowerCase() + "." + pair.getKey().toLowerCase(), pair.getValue());
			}
		}
		return true;
	}
	

	public String GetProperty(String key) {
		return mProperties.get(key.toLowerCase());
	}

	private HashMap<String,String> mProperties;
}
