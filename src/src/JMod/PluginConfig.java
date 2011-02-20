package JMod;

import java.net.URL;
import java.util.HashMap;

public class PluginConfig extends ConfigFile {
	public PluginConfig() {
		super();
		mProperties = new HashMap<String, String>();
	}

	public boolean LoadFromURL(URL url) throws DownloadException {
		System.out.println("Loading from url: " + url.toString());
		
		if(!super.LoadFromURL(url))
			return false;
		
		if(mProperties.containsKey("ERROR.code")) {
			int code = Integer.parseInt(GetProperty("ERROR.code"));
			String desc = GetProperty("ERROR.desc");
			if(code == 404) {
				System.out.println("Plugin not found: " + desc);
			} else {
				System.out.println("Unknown error: " + desc);
			}
			return false;
		} else {
			return true;
		}
	}

}
