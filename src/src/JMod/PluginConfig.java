package JMod;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ini4j.*;
import org.ini4j.Profile.Section;

public class PluginConfig {
	
	public PluginConfig() {}
	
	public boolean LoadFromUrl(String config) throws IOException {
		
		URL url = new URL("http://opatut.dyndns.org:81/jmod/config/" + config);
		Ini ini = new Ini();
		ini.load(new InputStreamReader(url.openStream()));
		for(Entry<String, Section> entry: ini.entrySet()) {
			for(Entry<String,String> pair: entry.getValue().entrySet()) {
				mProperties.put(entry.getKey() + "." + pair.getKey(), pair.getValue());
			}
		}
		return true;
	}

	private HashMap<String,String> mProperties;
}
