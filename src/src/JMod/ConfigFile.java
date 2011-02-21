package JMod;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import JMod.DownloadException.Reason;

public class ConfigFile {
	/**
	 * default constructor
	 */
	public ConfigFile() {
		mProperties = new HashMap<String, String>();
	}

	/**
	 * Loads the config file from the specified location.
	 * @param file The remote URL to load from.
	 * @return true if successful, false on error
	 * @throws DownloadException 
	 */
	public boolean LoadFromURL(URL url) throws DownloadException {
		try {
			mSource = url.toString();
			LoadFromReader(new InputStreamReader(url.openStream()));
			Parse();
			return true;
		} catch (IOException e) {
			throw new DownloadException(Reason.PLUGIN_NOT_FOUND, "?", e.getMessage());
		}
	}
	
	/**
	 * Loads the config file from the specified location. Creates a new file if it doesnt exist.
	 * @param file The local file to load from. 
	 * @return true if successful, false on error
	 */
	public boolean LoadFromFile(File file) {
		return LoadFromFile(file, true);
	}
	
	/**
	 * Loads the config file from the specified location.
	 * @param file The local file to load from.
	 * @param create_if_not_exists if true, the file is created if it does not exist 
	 * @return true if successful, false on error
	 */
	public boolean LoadFromFile(File file, boolean create_if_not_exists) {
		try {
			if(!file.exists()) {
				if(create_if_not_exists) {
					file.createNewFile();
				} else {
					System.err.println("Config file does not exist: " + file.getPath());
					return false;
				}
			}
			
			mSource = file.getPath();
			LoadFromReader(new FileReader(file));
			Parse();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Reads the reader into the config file.
	 * @param reader the reader to use
	 * @throws IOException
	 */
	private void LoadFromReader(Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);
		String line;
		while ((line = in.readLine()) != null)
			mContent += line + "\n";
		in.close();
	}
	
	/**
	 * parses a line of a config file
	 * @param line the line to parse
	 */
	public void ParseLine(String line) {
		// do no care about indentation
		line = line.trim();
		
		// comment lines start with "#"
		if(line.startsWith("#"))
			return;
		
		// split at first "=" sign
		String[] keyvalue = line.split("=", 2);
		
		// invalid line
		if(keyvalue.length != 2 || keyvalue[0].trim().length() == 0) {
			System.err.println("WARNING: Invalid config line in " + mSource);
			System.err.println(">  " + line);
			System.err.println("Skipping line.");
			return;
		}
		
		// everything went well
		SetProperty(keyvalue[0].trim(), keyvalue[1].trim());
	}
	
	/**
	 * Parses the earlier loaded config file.
	 */
	public void Parse() {
		// parse only if there is content
		if(mContent != null && mContent.length() > 0) {
			String[] lines = mContent.split("\n");
			if(lines.length > 0) {
				for(String line: lines) {
					ParseLine(line);
				}
				return;
			}
		}
		
		// something went wrong
		System.err.println("Empty or invalid plugin config file at");
		System.err.println("> " + mSource);
	}
	
	/**
	 * Saves the properties to a file.
	 * @param file the destination file
	 */
	public void SaveToFile(File file) {
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(GetContentString());
			out.close();
		}catch (IOException e){
			System.err.println("Could not write to file: ");
			System.err.println("> " + file.getPath());			
		}
	}
	
	/**
	 * Generates the file content from the property list.
	 * @return the new file content
	 */
	private String GetContentString() {
		String content = "";
		for(Entry<String, String> e: mProperties.entrySet()) {
			content += e.getKey() + " = " + e.getValue() + System.getProperty("line.separator");
		}
		return content;
	}
	

	/**
	 * Returns the property with a given key from the config file.
	 * @param key the key to look for
	 * @return the property, or if none found, <b>null</b>
	 */
	public String GetProperty(String key) {
		return GetProperty(key.toLowerCase(), null);
	}
	
	/**
	 * Returns the property with a given key from the config file.
	 * @param key the key to look for
	 * @param default_value the value to return if no property is found
	 * @return the property to the given key or default_value, if no property is found
	 */
	public String GetProperty(String key, String default_value) {
		if(mProperties.containsKey(key.toLowerCase()))
			return mProperties.get(key.toLowerCase());
		else
			return default_value;
	}
	
	/**
	 * Sets a new property.
	 * @param key the property key
	 * @param value the property value
	 */
	public void SetProperty(String key, String value) {
		mProperties.put(key.toLowerCase(), value);
	}
	
	/**
	 * Get a HashMap of all entries.
	 * @return HashMap of all entries
	 */
	public HashMap<String, String> GetEntries() {
		return mProperties;
	}
	
	private String mSource;
	private String mContent;
	protected HashMap<String, String> mProperties;
}
