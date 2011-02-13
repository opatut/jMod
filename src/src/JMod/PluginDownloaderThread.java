package JMod;

import java.io.*;
import java.net.URL;

public class PluginDownloaderThread extends Thread{
	
	public PluginDownloaderThread(String name, URL url, File destination) {
		mCancelDownload = false;
		setName("Plugin download thread");
		setDaemon(true);
		mName = name;
		mURL = url;
		mDestination = destination;
	}

	public void Cancel() {
		mCancelDownload = true;
	}

	public void run() {
		try{
			byte buffer[] = new byte[4096];
	        DataInputStream in = new DataInputStream(mURL.openStream());
	        DataOutputStream out = new DataOutputStream(new FileOutputStream(mDestination));
	        
	        for(int i = 0; (i = in.read(buffer)) >= 0;) {
	            out.write(buffer, 0, i);
	            if(mCancelDownload) 
	            	break;  
	        }
	        in.close();
	        out.close();
	        
	        PluginLoader.getInstance().RefreshInstalledPluginList();
	        PluginLoader.getInstance().PluginDownloadFinished(mName);
		} catch(IOException e) {}
	}
	
	private URL mURL;
	private File mDestination;
	private boolean mCancelDownload;
	private String mName;
}
