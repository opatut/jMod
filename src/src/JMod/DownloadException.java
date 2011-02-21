package JMod;

import java.io.IOException;

public class DownloadException extends IOException {

	public DownloadException(Reason reason, String plugin, String msg) {
		Message = msg;
		PluginName = plugin;
		Reason = reason;
	}
	
	public String Message;
	public String PluginName;
	public Reason Reason;
	
	public enum Reason {
		SERVER_NOT_FOUND,
		PLUGIN_NOT_FOUND,
		CONFIG_NOT_FOUND
	}
	
	private static final long serialVersionUID = 6905162139874108743L;
}
